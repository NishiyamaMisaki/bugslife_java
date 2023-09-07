  $(document).ready(function () {
  // 表示順をソートする(準備)
  var tabale = document.getElementById(".table");
  var displayOrderArray = [];
  var displayOrderElement = document.querySelectorAll(".display-order");
  displayOrderElement.forEach(function (element) {
    displayOrderArray.push(element.textContent);
  });

  // ソートの実行
  displayOrderArray.sort(function (a, b) {
    return a - b;
  });

  // ソート結果をHTMLへ反映
  displayOrderArray.forEach(function (displayOrder, index) {
    displayOrderElement[index].textContent = displayOrder;
  });


  // ツールチップの表示
  var tooltipTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-toggle="tooltip"]')
  );
  var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl, {
      trigger: "click",
    });
  });
});
