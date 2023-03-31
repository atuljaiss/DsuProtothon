function confirmServices() {
    
    let services = []
    $("input:checkbox[name=service]:checked").each(function(){
        services.push($(this).val());
    });

    $.ajax({

        url: "http://localhost:8080/rider/register",

        method: "POST",

        headers : {
            "Content-Type": "application/json"
        },

        data: JSON.stringify({
            services: services,
            phone: sessionStorage.getItem("phone")
        }),

        success: function(data) {
            sessionStorage.setItem("riderId", data.id)
            redirect("book-ride.html")
        }
    })
}