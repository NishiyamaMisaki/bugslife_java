$(document).ready(function () {
  $("#category-form").on("submit", function (event) {
    if (!validateForm()) {
      event.preventDefault();
      console.log("form validation failed");
    }
  });

  function validateForm() {
    var isValid = true;
    $(".form-control").each(function () {
        let id = $(this).attr("id");

        // codeのバリデーション
        if (id === "code") {
        let code = $(this).val();
        if (code === "" || !isPatternValid(code)) {
          $(this).addClass("is-invalid");
          isValid = false;
        } else {
          $(this).removeClass("is-invalid");
        }
        console.log(!isPatternValid(code));
      }

      // nameのバリデーション
      if (id === "name") {
        let name = $(this).val();
        if (name === "" || name.length > 20) {
          $(this).addClass("is-invalid");
          isValid = false;
        } else {
          $(this).removeClass("is-invalid");
        }
        console.log(name.length);
      }

      // desplay_orderのバリデーション
      if (id === "display_order") {
        let displayOrder = $(this).val();
        if (displayOrder === "" || displayOrder < 0 || displayOrder > 999) {
          $(this).addClass("is-invalid");
          isValid = false;
        } else {
          $(this).removeClass("is-invalid");
        }
      }

      // discritptionのバリデーション(300字以内)
      if (id === "description") {
          let description = $(this).val();
          if (description.length >= 301) {
            $(this).addClass("is-invalid");
            isValid = false;
          } else {
            $(this).removeClass("is-invalid");
          }
          console.log(description.length);
        }
      });
    return isValid;
  }

  // 半角大文字アルファベット3文字 + ハイフン + 半角数字3文字の計7文字
  function isPatternValid(code) {
    let pattern = /^([A-Z]{3}\-{1}[0-9]{3})$/;
    return pattern.test(code);
  }
});
