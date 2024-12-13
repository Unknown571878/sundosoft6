
// jQuery
(function() {
    var script = document.createElement('script');
    script.src = "https://code.jquery.com/jquery-3.6.0.min.js";
    document.head.appendChild(script);
})();

$(document).ready(function () {
    // 이메일 인증
    window.checkEmail = function() {
        var email = $('#email').val();
        $.ajax({
            type: "POST",
            url: "/member/mailConfirm",
            data: { "email": email },
            success: function (data) {
                alert("해당 이메일로 인증번호 발송이 완료되었습니다. \n 확인 해주시기 바랍니다.");
                checkMatchCode(data);
            }
        });
    };

    function checkMatchCode(data) {
        $('#emailConfirm').on("keyup", function () {
            if (data !== $('#emailConfirm').val()) {
                $('#emailCode').html("<span>인증번호가 잘못되었습니다.</span>").css({
                    "color": "#FA3E3E",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
            } else {
                $('#emailCode').html("<span>인증 성공</span>").css({
                    "color": "#0D6EFD",
                    "font-weight": "bold",
                    "font-size": "10px"
                });
            }
        });
    }

    // 아이디 중복 검사
    $('#checkIdButton').on('click', function () {
        checkDuplicateId();
    });

    function checkDuplicateId() {
        var loginid = $('#loginId').val().trim();

        if (loginid !== "") {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");

            $.ajax({
                url: "/checkDuplicateId",
                type: "POST",
                data: { "loginId": loginid },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function (data) {
                    if (data === 0) {
                        $('#loginIdError').html("<span>아이디 사용 가능</span>").css({
                            "color": "#0a53be",
                            "font-weight": "bold",
                            "font-size": "13px"
                        });
                    } else {
                        $('#loginIdError').html("<span>아이디 중복</span>").css({
                            "color": "#FA3E3E",
                            "font-weight": "bold",
                            "font-size": "13px"
                        });
                    }
                },
                error: function () {
                    $('#loginIdError').html("<span>오류가 발생했습니다. 다시 시도해주세요.</span>").css({
                        "color": "#FA3E3E",
                        "font-weight": "bold",
                        "font-size": "13px"
                    });
                }
            });
        } else {
            $('#loginIdError').html("");
        }
    }

    // 비밀번호 일치 검사
    $('#userPasswordCheck').on('input', function () {
        checkMatchPassword();
    });

    function checkMatchPassword() {
        var password = $('#userPassword').val();
        var passwordCheck = $('#userPasswordCheck').val();

        if (password === passwordCheck) {
            $('#passwordError').html("<span>비밀번호가 일치합니다</span>").css({
                "color": "#0a53be",
                "font-weight": "bold",
                "font-size": "13px"
            });
        } else {
            $('#passwordError').html("<span>비밀번호가 일치하지 않습니다</span>").css({
                "color": "#FA3E3E",
                "font-weight": "bold",
                "font-size": "13px"
            });
        }
    }
});