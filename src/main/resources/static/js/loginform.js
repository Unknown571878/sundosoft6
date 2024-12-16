// Daum Postcode API
(function() {
    var script = document.createElement('script');
    script.src = "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js";
    document.head.appendChild(script);
})();

window.execPostCode = function() {
    new daum.Postcode({
        oncomplete: function(data) {
            document.querySelector("input[name='address.zipcode']").value = data.zonecode;
            document.querySelector("input[name='address.address']").value = data.address;
            document.querySelector("input[name='address.detailAddr']").focus();
        }
    }).open();
};