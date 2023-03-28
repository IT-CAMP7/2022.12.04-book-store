function showMessage() {
    var messageDiv = document.getElementById("message");
    if(messageDiv.textContent != "") {
        messageDiv.style.display = "block";
        setTimeout(function(){
            document.getElementById("message").style.display = "none";
            }, 3000);
    }
}

function showCustomMessage(message) {
    var messageDiv = document.getElementById("message");
    messageDiv.innerHTML = message;
    messageDiv.style.display = "block";
    setTimeout(function(){
        document.getElementById("message").style.display = "none";
        messageDiv.innerHTML = "";
        }, 3000);
}

window.onload = showMessage;

function addToCart(bookId) {
    $.ajax({
        url: "http://localhost:8085/api/v1/cart/"+bookId,
        method: "GET",
        statusCode: {
            200: function() {
                showCustomMessage("Dodano do koszyka");
            },
            400: function(xhr) {
                showCustomMessage(xhr.responseText);
            }
        }
    });
}