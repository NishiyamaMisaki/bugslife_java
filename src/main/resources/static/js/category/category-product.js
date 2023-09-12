$(document).ready(function () {
  let categoryId = document.getElementById("categoryId").getAttribute("val");
  let action = document.getElementById("action").getAttribute("value");


  $.ajax({
    url: "/categories/" + categoryId + "/productRelation",
    type: "GET",
    dataType: "json",
    contentType: "application/json",
  })
    .done(function (data) {
      var categoryProducts = data;
      console.log("APIコールが成功");
      // チェックボックスにチェックを入れる処理
      categoryProducts.forEach(function (categoryProduct) {
        $("#checkbox-" + categoryProduct.productId).prop("checked", true);
      });
    })
    .fail(function () {
      // APIコールが失敗した場合の処理
      console.log("APIコールが失敗しました。");
    });

    $("#update-button").click(function () {
    var checkedIds = $(".form-check-input:checked")
      .map(function () {
        return this.value;
      })
      .get();

      // 作成更新時に紐付けが存在しない場合はスキップ
      if (action == "true") {
        if (!validation(checkedIds)) {
          return false;
        }
      }

      // JSON形式に変換
      let postData = {
      productIds: checkedIds,
      };
      let postDataJson = JSON.stringify(postData);

    $.ajax({
      url: "/api/categories/" + categoryId + "/updateCategoryProduct",
      type: "POST",
      dataType: "json",
      contentType: "application/json",
      data: postDataJson,
    })
      .done(function (data) {
        $("#success-message")
          .text(data)
          .show()
          .fadeOut(3000);
        })
      .fail(function () {
        // APIコールが失敗した場合の処理
        validation(checkedIds);
        console.log("APIコールが失敗しました。");
      });
    });

    validation = function (checkedIds) {
      if (checkedIds.length == 0) {
        $("#error-message")
        .text(
          "商品を選択して更新か、不要な場合はカテゴリー一覧を選択して下さい。"
          )
          .show()
          .fadeOut(3000);
          return false;
        }
    return true;
  };
});
