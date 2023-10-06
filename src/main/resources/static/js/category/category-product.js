$(document).ready(function () {
  let categoryId = document.getElementById("categoryId").getAttribute("val");
  let action = document.getElementById("action").getAttribute("value");

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

    $.ajax({
      url: "/api/categories/" + categoryId + "/updateCategoryProduct",
      type: "POST",
      dataType: "text",
      contentType: "application/json",
      data: JSON.stringify(postData),
    })
    .done(function (data, textStatus, jqXHR) {
      if (checkedIds.length === 0) {
        $("#error-message")
          .text("商品を選択して更新か、不要な場合はカテゴリー一覧を選択して下さい。")
          .show()
          .fadeOut(3500);
      } else {
        $("#success-message").text("更新に成功しました。").show().fadeOut(3000);
      console.log("通信に成功しました。", "ステータスコード：" + jqXHR.status);
      }
    })
    .fail(function (jqXHR, textStatus, errorThrown) {
      $("#error-message").text("更新に失敗しました。").show().fadeOut(3000);
      console.log("通信に失敗しました。", "ステータスコード：" + jqXHR.status);
    });
  });

  function validation(checkedIds) {
    return true;
  }
});
