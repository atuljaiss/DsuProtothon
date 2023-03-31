let emergenyBookingPriceList = [];
let compareBookingPriceList = [];
let selectedRideDriverId = 0;
let tripType;

window.onload = function() {

    getRideOptions()
}

function getRideOptions() {
    $.ajax({

        url: "http://localhost:8080/ride/book",

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        data: JSON.stringify({
            riderId: sessionStorage.getItem("riderId"),
            from: sessionStorage.getItem("from"),
            to: sessionStorage.getItem("to")
        }),

        success: function (response) {

            console.log(response)
            if (response.comparePriceBooking != null) {
                tripType = "COMPARE_PRICE_RIDE"
                let bookingOptions = response.comparePriceBooking.tripOptions
                console.log(bookingOptions)
                console.log(response)
                compareBookingPriceList = bookingOptions
                let html = "";
                for (let i = 0; i < bookingOptions.length; i++) {
                    if(i == 0)selectedRideDriverId = bookingOptions[i].driver.driverId
                    let option = bookingOptions[i];
                    html += "<div id='ride-details-" + i + "' class=\"ride-details-wrapper\" onClick='selectRide(" + option.driver.driverId + "," + i + ")'>\n" +
                        "                <div class=\"driver-image-wrapper\">\n" +
                        "                    <img class=\"driver-avatar\"\n" +
                        "                         src=\"" + option.driver.profilePic + "\" />\n" +
                        "                </div>\n" +
                        "                <div class=\"car-details-wrapper\">\n" +
                        "                    <div class=\"service-provider-container\"> " + option.rideServiceProviderContext.serviceProvider + " </div>\n" +
                        "                    <div class=\"car-model-container\"> " + option.driver.carModel + " </div>\n" +
                        "                    <div class=\"car-number-container\"> " + option.driver.carRegistrationNumber + " </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"price-button-wrapper\">\n" +
                        "                    <div class=\"price-text-wrapper\"> INR " + parseFloat(option.rideServiceProviderContext.price).toFixed(2) + " </div>\n" +
                        "                </div>\n" +
                        "            </div>";
                }
                $("#ride-option-list").html(html)
                $("#ride-details-0").addClass('ride-selected')
            }

            else if (response.emergencyPriceBooking != null) {

                tripType = "EMERGENCY_RIDE"
                let bookingOptions = response.emergencyPriceBooking.tripOptions
                emergenyBookingPriceList = bookingOptions
                $("#warning-text-custom-price").removeClass("hidden")

                let html = "";
                for (let i = 0; i < bookingOptions.length; i++) {
                    if(i == 0)selectedRideDriverId = bookingOptions[i].driver.driverId
                    let option = bookingOptions[i];
                    html += "<div id='ride-details-" + i + "' class=\"ride-details-wrapper\" onClick='selectRide(" + option.driver.driverId + "," + i + ")'>\n" +
                        "                <div class=\"driver-image-wrapper\">\n" +
                        "                    <img class=\"driver-avatar\"\n" +
                        "                         src=\"" + option.driver.profilePic + "\" />\n" +
                        "                </div>\n" +
                        "                <div class=\"car-details-wrapper\">\n" +
                        "                    <div class=\"service-provider-container\"> " + option.rideServiceProviderContext.serviceProvider + " </div>\n" +
                        "                    <div class=\"car-model-container\"> " + option.driver.carModel + " </div>\n" +
                        "                    <div class=\"car-number-container\"> " + option.driver.carRegistrationNumber + " </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"price-button-wrapper\">\n" +
                        "                    <div class=\"price-text-wrapper\"> INR " + parseFloat(option.rideServiceProviderContext.price).toFixed(2) + " </div>\n" +
                        "                </div>\n" +
                        "            </div>";
                }
                $("#ride-option-list").html(html)
                $("#ride-details-0").addClass('ride-selected')
            }
 
            else if (response.splitBooking != null) {

                tripType = "SPLIT_RIDE"
                let bookingOptions = response.splitBooking.tripOptions
                emergenyBookingPriceList = bookingOptions
                $("#warning-text-split").removeClass("hidden")
                let html = "";
                selectedRideDriverId = ""
                for (let i = 0; i < bookingOptions.length; i++) {
                    let option = bookingOptions[i];
                    let splitFrom  = "";
                    let splitTo = "";

                    selectedRideDriverId += bookingOptions[i].driver.driverId + "and"

                    if(option.rideServiceProviderContext.splitInfo.pickUp.indexOf(',') > -1)
                        splitFrom = option.rideServiceProviderContext.splitInfo.pickUp.split(',')[0]
                    else splitFrom = option.rideServiceProviderContext.splitInfo.pickUp

                    if(option.rideServiceProviderContext.splitInfo.drop.indexOf(',') > -1)
                        splitTo = option.rideServiceProviderContext.splitInfo.drop.split(',')[0]
                    else splitTo = option.rideServiceProviderContext.splitInfo.drop

                    html += "<div>" +
                        "<div id='ride-details-" + i + "' class=\"ride-details-wrapper\" onClick='selectRide(" + option.driver.driverId + "," + i + ")'>\n" +
                        "                <div class=\"driver-image-wrapper\">\n" +
                        "                    <img class=\"driver-avatar\"\n" +
                        "                         src=\"" + option.driver.profilePic + "\" />\n" +
                        "                </div>\n" +
                        "                <div class=\"car-details-wrapper\">\n" +
                        "                    <div class=\"service-provider-container\"> " + option.rideServiceProviderContext.serviceProvider + " </div>\n" +
                        "                    <div class=\"car-model-container\"> " + option.driver.carModel + " </div>\n" +
                        "                    <div class=\"car-number-container\"> " + option.driver.carRegistrationNumber + " </div>\n" +
                        "                </div>\n" +
                        "                <div class=\"price-button-wrapper\">\n" +
                        "                    <div class=\"price-text-wrapper\"> INR " + parseFloat(option.rideServiceProviderContext.price).toFixed(2) + " </div>\n" +
                        "                </div>\"" +
                        "                " +
                        "            </div>" +
                        "<div class='split-info'> " + splitFrom + " TO " + splitTo + " </div>" +
                        "</div>";
                }
                $("#ride-option-list").html(html)
                $("#ride-details-0").addClass('ride-selected')
            }

            $(".page-wrapper").removeClass('hidden')
            $(".wait-screen").hide()
        }
    })
}

function selectRide(driverId, index) {
    $("[id^=ride-details]").removeClass('ride-selected')
    $("#ride-details-" + index).addClass('ride-selected')
    selectedRideDriverId = driverId;
}

function confirmRide() {

    $.ajax({

        url: "http://localhost:8080/trip/confirm?driverId=" + selectedRideDriverId,

        success: function(response) {
            $("#ride-option-list").html("<div class='confirm-text'><h1>Your trip is confirmed!</h1></div>");
            $(".big-yellow-button").html("Track your ride");
        }
    })
}