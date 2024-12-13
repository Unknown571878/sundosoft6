// Daum Postcode API
(function() {
    var script = document.createElement('script');
    script.src = "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
    document.head.appendChild(script);
})();


// Daum 우편번호 API 연동
window.sample6_execDaumPostcode = function() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.querySelector("input[name='address.zipcode']").value = data.zonecode;
            document.querySelector("input[name='address.address']").value = data.address;
            document.querySelector("input[name='address.detailAddr']").focus();
        }
    }).open();
};