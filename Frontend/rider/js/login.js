function getOtp() {
    sessionStorage.setItem("phone", $("#phone-number").val())
    redirect("otp.html")
} 