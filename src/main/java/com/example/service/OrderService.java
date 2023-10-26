package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.enums.OrderStatus;
import com.example.enums.PaymentStatus;
import com.example.form.OrderForm;
import com.example.model.Order;
import com.example.model.OrderDelivery;
import com.example.model.OrderPayment;
import com.example.model.OrderProduct;
import com.example.model.Tax;
import com.example.repository.OrderDeliveryRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Transactional(readOnly = true)
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderDeliveryRepository orderDeliveryRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Optional<Order> findOne(Long id) {
		return orderRepository.findById(id);
	}

	@Transactional(readOnly = false)
	public Order save(Order entity) {
		return orderRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public Order create(OrderForm.Create entity) {
		Order order = new Order();
		order.setCustomerId(entity.getCustomerId());
		order.setShipping(entity.getShipping());
		order.setNote(entity.getNote());
		order.setPaymentMethod(entity.getPaymentMethod());
		order.setStatus(OrderStatus.ORDERED);
		order.setPaymentStatus(PaymentStatus.UNPAID);
		order.setPaid(0.0);

		var orderProducts = new ArrayList<OrderProduct>();
		entity.getOrderProducts().forEach(p -> {
			var product = productRepository.findById(p.getProductId()).get();
			var orderProduct = new OrderProduct();
			orderProduct.setProductId(product.getId());
			orderProduct.setCode(product.getCode());
			orderProduct.setName(product.getName());
			orderProduct.setQuantity(p.getQuantity());
			orderProduct.setPrice((double)product.getPrice());
			orderProduct.setDiscount(p.getDiscount());
			orderProduct.setTaxRate(Tax.get(product.getTaxType()).getRate());
			orderProducts.add(orderProduct);
		});

		// 計算
		var total = 0.0;
		var totalTax = 0.0;
		var totalDiscount = 0.0;
		for (var orderProduct : orderProducts) {
			var price = orderProduct.getPrice();
			var quantity = orderProduct.getQuantity();
			var discount = orderProduct.getDiscount();
			var tax = 0.0;
			/**
			 * 税額を計算する
			 */
			if (orderProduct.getTaxIncluded()) {
				// 税込みの場合
				tax = price * quantity * orderProduct.getTaxRate() / (100 + orderProduct.getTaxRate());
			} else {
				// 税抜きの場合
				tax = price * quantity * orderProduct.getTaxRate() / 100;
			}
			// 端数処理
			tax = switch (orderProduct.getTaxRounding()) {
			case "round" -> Math.round(tax);
			case "floor" -> Math.floor(tax);
			case "ceil" -> Math.ceil(tax);
			default -> tax;
			};
			var subTotal = price * quantity + tax - discount;
			total += subTotal;
			totalTax += tax;
			totalDiscount += discount;
		}
		order.setTotal(total);
		order.setTax(totalTax);
		order.setDiscount(totalDiscount);
		order.setGrandTotal(total + order.getShipping());
		order.setOrderProducts(orderProducts);

		orderRepository.save(order);

		return order;

	}

	@Transactional()
	public void delete(Order entity) {
		orderRepository.delete(entity);
	}

	@Transactional(readOnly = false)
	public void createPayment(OrderForm.CreatePayment entity) {
		var order = orderRepository.findById(entity.getOrderId()).get();
		/**
		 * 新しい支払い情報を登録する
		 */
		var payment = new OrderPayment();
		payment.setType(entity.getType());
		payment.setPaid(entity.getPaid());
		payment.setMethod(entity.getMethod());
		payment.setPaidAt(entity.getPaidAt());

		/**
		 * 支払い情報を更新する
		 */
		// orderのorderPaymentsに追加
		order.getOrderPayments().add(payment);
		// 支払い済み金額を計算
		var paid = order.getOrderPayments().stream().mapToDouble(p -> p.getPaid()).sum();
		// 合計金額から支払いステータスを判定
		var paymentStatus = paid > order.getGrandTotal() ? PaymentStatus.OVERPAID
				: paid < order.getGrandTotal() ? PaymentStatus.PARTIALLY_PAID : PaymentStatus.PAID;

		// 更新
		order.setPaid(paid);
		order.setPaymentStatus(paymentStatus);
		orderRepository.save(order);
	}

	/**
	 * CSVインポート処理
	 *
	 * @param file
	 * @throws IOException
	 */
	@Transactional
	public void importCSV(MultipartFile file) throws IOException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line = br.readLine(); // 1行目はヘッダーなので読み飛ばす
			List<OrderDelivery> orders = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				final String[] split = line.replace("\"", "").split(",");
				final Integer id = Integer.parseInt(split[0]);
				final String shippingCode = split[1];

				SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
				Date shippingDate = null;

				if (!split[2].isEmpty()) {
					try {
						if (split[2].equals("null")) {
							// "null" の場合、現在日時を shippingDate に設定
							shippingDate = new Date(); // 現在日時を取得
						} else {
							shippingDate = inputDateFormat.parse(split[2]);
						}
					} catch (ParseException e) {
						// ログにエラーメッセージを記録
						System.err.println("発送日が不正です: " + split[2]);
					}
				} else {
					split[2] = "0000-00-00";
					shippingDate = null; // shippingDateをnullに設定する
				}

				Date deliveryDate = null;
				if (!split[3].isEmpty()) {
					try {
						if (split[3].equals("null")) {
							deliveryDate = new Date(); // 現在日時を取得
						} else {
							deliveryDate = inputDateFormat.parse(split[3]);
						}
					} catch (ParseException e) {
						// ログにエラーメッセージを記録
						System.err.println("発送日が不正です: " + split[2]);
					}
				} else {
					deliveryDate = null; // deliveryDateをnullに設定する
				}

				final String deliveryTimezone = split[4];
				final String status = split[5];

				OrderDelivery order = new OrderDelivery(id, shippingCode, shippingDate, deliveryDate, deliveryTimezone,
						status);

				orders.add(order);
			}
			// OrderDeliveryエンティティをデータベースに保存
			orderDeliveryRepository.saveAll(orders);

		} catch (IOException e) {
			throw new RuntimeException("ファイルが読み込めません", e);
		}
	}

	public List<OrderDelivery> findAllOrderDelivery() {
		return orderDeliveryRepository.findAll();
	}
}