$(document).ready(function () {
  // テーブル要素を取得
  var table = document.getElementById("category-table");
  var tbody = table.querySelector("tbody"); // tbody要素を取得

  // tbody内の行を取得
  var rows = Array.from(tbody.rows);

  // ソートの実行
  rows.sort(function (a, b) {
    var a = parseInt(a.querySelector(".display-order").textContent);
    var b = parseInt(b.querySelector(".display-order").textContent);
    return a - b;
  });

  // ソート結果をHTMLへ反映
  for (var i = 0; i < rows.length; i++) {
    tbody.appendChild(rows[i]); // 行を順番に追加
  }

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
