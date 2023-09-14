$(document).ready(function () {
    $("#companies-form").on("submit", function (event) {
    if (!validateForm()) {
        event.preventDefault();
        console.log("form validation failed");
    }
    });

    function validateForm() {
        var isValid = true;
        $(".form-control").each(function () {
            let id = $(this).attr("id");

        // nameのバリデーション
        if (id === "name") {
            let name = $(this).val();
            if (name === "" || name === null) {
                $(this).addClass("is-invalid");
                isValid = false;
            } else {
                $(this).removeClass("is-invalid");
            }
            console.log(name.length);
        }});
    }
});
