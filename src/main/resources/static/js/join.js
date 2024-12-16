$(document).ready(function (){
    const emailInput = $('#email');
    const codeInput = $('#code');
    const hiddenEmailField = $('#hiddenEmail');
    const emailBtn = $('#email-btn');
    const codeBtn = $('#code-btn');

    // 이메일 인증번호 발송
    $('#checkEmail').on('submit', function (event) {
        event.preventDefault();
        emailBtn.prop("disabled", true)
                .text("인증 진행 중...");

        $.ajax({
            url: '/api/mail/emailCheck',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email: emailInput.val() }),
            success: function (response) {
                alert('이메일 인증 번호 전송 완료');
                emailInput.prop('readonly', true); // 이메일 입력란 비활성화
                $('#checkCode').removeClass('d-none'); // 인증번호 확인 폼 표시
            },
            error: function (xhr) {
                alert('이메일 인증 번호 전송 실패');
                console.error(xhr.responseText);
                emailInput.prop('readonly', false);
                emailBtn.prop("disabled", false).text("이메일 인증");
                emailBtn.removeClass('btn-outline-primary').addClass('btn-outline-success');
                emailBtn.text("인증 완료");
                codeBtn.removeClass('btn-outline-primary').addClass('btn-outline-success');
                codeBtn.text("인증 완료");
            }
        });
    });

    // 인증번호 확인
    $('#checkCode').on('submit', function (event) {
        event.preventDefault();
        const email = emailInput.val();

        $.ajax({
            url: '/api/mail/codeCheck',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email: email, code: codeInput.val() }),
            success: function (response) {
                alert('인증 성공');
                codeInput.prop('readonly', true);
                hiddenEmailField.val(email);
                console.log('Hidden email field value:', hiddenEmailField.val());
                emailBtn.prop('disabled', true).text('인증 성공');
            },
            error: function (error) {
                alert('인증 실패. 잘못된 인증번호입니다.');
                codeInput.val('');
                emailBtn.prop("disabled", false).text("이메일 인증");
                emailInput.prop('readonly', false).focus();
            }
        });
    });
});
