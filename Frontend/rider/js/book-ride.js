function initMap(fromAutoComplete, toAutoComplete) {

    if(fromAutoComplete == undefined || toAutoComplete == undefined) return;

    let map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 22.5, lng: 88.3},
        zoom: 13
    });
 
    let marker1 = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });

    let marker2 = new google.maps.Marker({
        map: map,
        anchorPoint: new google.maps.Point(0, -29)
    });

    fromAutoComplete.addListener('place_changed', function() {
        marker1.setVisible(false);
        let place = fromAutoComplete.getPlace();
        if (!place.geometry) {
            window.alert("Autocomplete's returned place contains no geometry");
            return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }
        marker1.setIcon(({
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(35, 35)
        }));
        marker1.setPosition(place.geometry.location);
        marker1.setVisible(true);
        drawLine(fromAutoComplete, toAutoComplete, map)
    });

    toAutoComplete.addListener('place_changed', function() {
        marker2.setVisible(false);
        let place = toAutoComplete.getPlace();
        if (!place.geometry) {
            window.alert("Autocomplete's returned place contains no geometry");
            return;
        }

        // If the place has a geometry, then present it on a map.
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }
        marker2.setIcon(({
            url: place.icon,
            size: new google.maps.Size(71, 71),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(17, 34),
            scaledSize: new google.maps.Size(35, 35)
        }));
        marker2.setPosition(place.geometry.location);
        marker2.setVisible(true);
        drawLine(fromAutoComplete, toAutoComplete, map)
    });
}

function setUpAutoComplete() {
    let from = document.getElementById('from');
    let to = document.getElementById('to');
    let fromAutoComplete = new google.maps.places.Autocomplete(from);
    let toAutoComplete = new google.maps.places.Autocomplete(to);

    initMap(fromAutoComplete, toAutoComplete)
}

function drawLine(fromAutoComplete, toAutoComplete, map) {

    let fromPlace = fromAutoComplete.getPlace();
    let toPlace = toAutoComplete.getPlace();

    let lineCoordinates = [
        {lat: fromPlace.geometry.location.lat(), lng: fromPlace.geometry.location.lng()},
        {lat: toPlace.geometry.location.lat(), lng: toPlace.geometry.location.lng()}
    ];
    let linePath = new google.maps.Polyline({
        path: lineCoordinates,
        geodesic: true,
        strokeColor: '#000000'
    });

    linePath.setMap(map);
}

function bookRide() {
    sessionStorage.setItem("from", $("#from").val());
    sessionStorage.setItem("to", $("#to").val());
    redirect("ride-options.html")
}