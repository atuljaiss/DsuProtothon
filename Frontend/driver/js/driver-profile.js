let rideId;
let waitingForAcceptance = false;
let declineReason;
let waitingForDeclineReason = false;
let currentLocation;
let tripConfirmed = false;

window.onload = function() {

    currentLocation = getParameterByName("currentlocation")
    if(currentLocation == undefined) currentLocation = "Jayanagar"
    $(".fact_data_container").html(currentLocation)

    $("#name").html(getDriverDetail("name"))
    $("#phoneNumber").html(getDriverDetail("phoneNumber"))
    $("#address").html(getDriverDetail("address"))
    $("#regNumber").html(getDriverDetail("driverRegistrationIdWithServiceProvider"))
    $("#carModel").html(getDriverDetail("carModel"))
    $("#carNumber").html(getDriverDetail("carRegistrationNumber"))
    $("#driverRegisteredWith").html(getDriverDetail("driverRegisteredWith"))
    $("#dp-wrapper").html("<img class='dp-image' src='"+ getDriverDetail("profilePic") +"' />")

    lookForBooking()
    checkForTrip()

    $(".decline-reason-text").click(function(){
        $(".decline-reason-text").removeClass("selected-decline-reason")
        $(this).addClass("selected-decline-reason")
        declineReason = $(this).attr('id')
    })
}

function getDriverDetail(key) { 
    let driver = JSON.parse(sessionStorage.getItem("driverProfile"))
    console.log(driver[key])
    return driver[key]
    
}

function checkForTrip() {

    $.ajax({

        url: "http://localhost:8080/trip/check?driverId=" + getDriverDetail("driverId"),

        success: function(response) {
            if(response.status) {
                tripConfirmed = true;
                $(".waiting-text").html("Your trip is confirmed. You can navigate to pick-up location using your " + getDriverDetail("driverRegisteredWith") + " app")
            }
            else {
                setTimeout(function(){
                    checkForTrip()
                }, 2000)
            }
        }
    })
}

function lookForBooking() {

    $.ajax({

        url: "http://localhost:8080/ride?serviceProvider=" + getDriverDetail("driverRegisteredWith") + "&currentLocation=" + currentLocation,

        success: function(response) {
            try{
                if(response.length == 0) {
                    $("#trip-notification").addClass("hidden")
                    return;
                }
                response = response[0]
                $("#pickUpLocation").html(response.from)
                $("#dropLocation").html(response.to)
                $("#amount").html("INR " + parseFloat(response.price).toFixed(2))
                $("#actual-price-text").html("Price offered INR " + parseFloat(response.price).toFixed(2))
                $("#towards").html(response.to);
                rideId = response.id;

                $("#trip-notification").removeClass("hidden")
            } catch(e) {
                console.error(e)
                $("#trip-notification").addClass("hidden")
            } finally {
                setTimeout(function(){
                    lookForBooking()
                }, 2000)
            }
        }
    })
}

function acceptRide() {
    $.ajax({

        url: "http://localhost:8080/ride/driver/accept?serviceProvider=" + getDriverDetail("driverRegisteredWith") + "&rideId=" + rideId + "&driverId=" + getDriverDetail("driverId"),

        success: function(response) {
            waitingForAcceptance = true;
            $("#waiting-screen").removeClass("hidden")
            setTimeout(function(){
                if(!tripConfirmed){
                    $("#waiting-screen").addClass("hidden")
                    tripConfirmed = false;
                }
            }, 45000)
        }
    })
}

function declineRide() {
    $("#trip-notification").css("height", "0px");
    $("#decline-popup").removeClass("hidden");
}

function handleDeclineReason() {
    $("#decline-popup").addClass("hidden");
    if(declineReason == "price") {
        $("#emergency-price-popup").removeClass("hidden");
    }
    else if(declineReason == "drop-location") {
        $("#split-ride-option-popup").removeClass("hidden");
    }
}

function submitEmergencyPrice() {
    let customPrice = $("#custom-price").val()
    $("#emergency-price-popup").addClass("hidden")
    $.ajax({

        url: "http://localhost:8080/ride/driver/declinewithcustomprice?serviceProvider=" + getDriverDetail("driverRegisteredWith") + "&rideId=" + rideId + "&driverId=" + getDriverDetail("driverId") + "&customPrice=" + customPrice,

        success: function(response) {
            waitingForAcceptance = true;
            waitingForDeclineReason = false;
            $("#waiting-screen").removeClass("hidden")
            setTimeout(function(){
                if(!tripConfirmed){
                    $("#waiting-screen").addClass("hidden")
                    tripConfirmed = false;
                }
            }, 45000)
        }
    })
}

function submitSplitPoint() {
    let splitPoint = $("#split-point").val()
    $("#split-ride-option-popup").addClass("hidden")
    $("#waiting-screen").removeClass("hidden")
    $.ajax({

        url: "http://localhost:8080/ride/driver/declinewithsplit?serviceProvider=" + getDriverDetail("driverRegisteredWith") + "&rideId=" + rideId + "&driverId=" + getDriverDetail("driverId") + "&splitPoint=" + splitPoint,

        success: function(response) {
            waitingForAcceptance = true;
            waitingForDeclineReason = false;
            console.log("HIDDEN")
            $("#waiting-screen").removeClass("hidden")
            setTimeout(function(){
                if(!tripConfirmed){
                    $("#waiting-screen").addClass("hidden")
                    tripConfirmed = false;
                }
            }, 45000)
        }
    })
}