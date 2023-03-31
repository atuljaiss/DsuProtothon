function getOtp() {
    sessionStorage.setItem("driverPhone", $("#phone-number").val())
    sessionStorage.setItem("registeredWith", $("#service-provider-select").val())
    redirect("otp.html")
}