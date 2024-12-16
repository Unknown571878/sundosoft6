// Id 설정
function validateId(input) {
    if (input.value.length < 4) {
        input.setCustomValidity('아이디는 5자리 이상이어야 합니다.');
    } else {
        input.setCustomValidity('');
    }
}

// 비밀 번호 설정
function validatePassword(input) {
    if (input.value.length < 6) {
        input.setCustomValidity('비밀번호는 6자리 이상이어야 합니다.');
    } else {
        input.setCustomValidity('');
    }
}

// 이름 설정
function validateName(input) {
    if (input.value.length < 2) {
        input.setCustomValidity('이름은 두 글자 이상이어야 합니다.');
    }else{
        input.setCustomValidity('');
    }
}

// 전화번호 설정
function validateTel(input) {
    // 정규 표현식: 한국 전화번호 형식 (예: 010-1234-5678 또는 02-123-4567)
    const phonePattern = /^(010)-([0-9]{4})-([0-9]{4})$/;

    if (!phonePattern.test(input.value)) {
        input.setCustomValidity('전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678 ');
    } else {
        input.setCustomValidity('');
    }
}

// 제목 설정
function validateTitle(input) {
    if (input.value.length < 0) {
        input.setCustomValidity('제목을 입력하세요.');
    } else {
        input.setCustomValidity('');
    }
}

// 내용 설정
function validateContent(input) {
    if (input.value.length < 10) {
        input.setCustomValidity('최소 10글자 이상 입력하셔야합니다.');
    } else {
        input.setCustomValidity('');
    }
}

// 날짜 설정
function validateDate(input) {
    const datePattern = /^([0-9]{4})-([0-9]{2})-([0-9]{2})$/;
    if (!datePattern.test(input.value)) {
        input.setCustomValidity('0000-00-00 형식으로 입력하세요');
    } else {
        input.setCustomValidity('');
    }
}
