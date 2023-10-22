package com.example.controller;

import java.util.List;
import java.util.Optional;

import com.example.constants.Message;
import com.example.model.Tax;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.service.TaxlistService;

@Controller
@RequestMapping("/taxlist")
public class TaxListController {

	@Autowired
	private TaxlistService taxlistService;

	@Autowired
	private ShopProductController shopProductController;

	/**
	 * 一覧画面表示
	 *
	 * @param model
	 * @param form
	 * @return
	 */
	@GetMapping
	public String index(Model model) {
		// 全件取得
		List<Tax> taxlist = taxlistService.findAll();

		model.addAttribute("taxlist", taxlist);

		return "taxlist/index";
	}

	/**
	 * 詳細画面表示
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/show/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Optional<Tax> taxlist = taxlistService.findOne(id);
			model.addAttribute("taxlist", taxlist.get());
		}
		return "taxlist/show";
	}

	/**
	 * 登録画面表示
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/create")
	public String create(Model model, @ModelAttribute Tax entity) {
		model.addAttribute("taxlist", entity);
		return "taxlist/form";
	}

	// 新規登録
	@PostMapping
	public String create(Model model, @Validated @ModelAttribute Tax entity, BindingResult result,
			RedirectAttributes redirectAttributes) {
		Tax taxlist = new Tax();
		try {
			taxlist = taxlistService.save(entity);
			redirectAttributes.addFlashAttribute("success", Message.MSG_SUCESS_INSERT);
			return "redirect:/taxlist/show/" + taxlist.getId();
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", Message.MSG_ERROR);
			e.printStackTrace();
			return "redirect:/taxlist";
		}
	}

	/**
	 * 編集画面表示
	 *
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Optional<Tax> taxlist = taxlistService.findOne(id);
			model.addAttribute("taxlist", taxlist.get());
		}
		return "taxlist/form";
	}

	// 更新
	@PutMapping
	public String update(Model model, @Validated @ModelAttribute Tax entity, BindingResult result,
			RedirectAttributes redirectAttributes) {
		Tax taxlist = null;
		try {
			taxlist = taxlistService.save(entity);
			redirectAttributes.addFlashAttribute("success", Message.MSG_SUCESS_UPDATE);
			return "redirect:/taxlist/show/" + taxlist.getId();
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", Message.MSG_ERROR);
			e.printStackTrace();
			return "redirect:/taxlist";
		}
	}

	// 削除
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Tax id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Optional<Tax> taxlist = taxlistService.findOne(id.getId());
				// Productに存在するか確認
				if (!shopProductController.isExistTax(taxlist.get().getId())) {
					taxlistService.delete(taxlist.get());
					redirectAttributes.addFlashAttribute("success", Message.MSG_SUCESS_DELETE);
				} else {
					redirectAttributes.addFlashAttribute("error", "この税率は商品に使用されているため削除できません。");
				}
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", Message.MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/taxlist";
	}

}