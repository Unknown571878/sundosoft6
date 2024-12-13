document.addEventListener("DOMContentLoaded", function() {
    var rememberIdCheck = document.getElementById("flexCheckDefault");
    var emailInput = document.getElementById("email"); // username을 email로 변경

    var savedEmail = localStorage.getItem("savedEmail"); // savedId를 savedEmail로 변경

    if (savedEmail) {
        emailInput.value = savedEmail;
        rememberIdCheck.checked = true;
    }

    rememberIdCheck.addEventListener("click", function() {
        if (rememberIdCheck.checked) {
            localStorage.setItem("savedEmail", emailInput.value);
        } else {
            localStorage.removeItem("savedEmail");
        }
    });
});