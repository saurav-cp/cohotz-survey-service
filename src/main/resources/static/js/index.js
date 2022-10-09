subscriptionsTable = $("#subscriptions-table").DataTable({
    data: [],
    columns: [{
            "data": "name"
        },
        {
            "data": "description"
        },
        {
            "data": "active",
            "render": function(data, type, row) {
                if (data === true) {
                    return '<div class="form-check form-switch"> <input class="form-check-input" type="checkbox" id="subs_active_' + row.name + '" onclick="updateSubscription(' + row.id + ', $(this));" checked></div>';
                } else {
                    return '<div class="form-check form-switch"> <input class="form-check-input" type="checkbox" id="subs_active_' + row.name + '" onclick="updateSubscription(' + row.id + ', $(this));"></div>';
                }
                return data;
            },
            "className": "dt-body-center"
        },
        {
            "data": "tenants"
        }
    ],
    rowCallback: function(row, data) {},
    filter: false,
    info: false,
    ordering: false,
    processing: true,
    retrieve: true,
    bPaginate: false,
    language: {
        emptyTable: "Nothing to show here"
    }
});

tenantsTable = $("#tenants-table").DataTable({
    data: [],
    columns: [{
            "data": "name"
        },
        {
            "data": "description"
        },
        {
            "data": "subscription"
        },
        {
            "data": "active",
            "render": function(data, type, row) {
                if (data === true) {
                    return '<div class="form-check form-switch"> <input class="form-check-input" type="checkbox" id="tenant_active_' + row.name + '" onclick="updateTenant(' + row.id + ', $(this));" checked></div>';
                } else {
                    return '<div class="form-check form-switch"> <input class="form-check-input" type="checkbox" id="tenant_active_' + row.name + '" onclick="updateTenant(' + row.id + ', $(this));"></div>';
                }
                return data;
            },
            "className": "dt-body-center"
        },
        {
            "data": "userCount"
        }
    ],
    rowCallback: function(row, data) {},
    filter: false,
    info: false,
    ordering: false,
    processing: true,
    retrieve: true,
    bPaginate: false,
    language: {
        emptyTable: "Nothing to show here"
    }
});

usersTable = $("#users-table").DataTable({
    data: [],
    columns: [{
            "data": "name"
        },
        {
            "data": "email"
        },
        {
            "data": "tenant"
        },
        {
            "data": "active",
            "render": function(data, type, row) {
                if (data === true) {
                    return '<div class="form-check form-switch"> <input class="form-check-input" type="checkbox" id="user_active_' + row.name + '" onclick="updateUser(' + row.id + ', $(this));" checked></div>';
                } else {
                    return '<div class="form-check form-switch"> <input class="form-check-input" type="checkbox" id="user_active_' + row.name + '" onclick="updateUser(' + row.id + ', $(this));"></div>';
                }
                return data;
            },
            "className": "dt-body-center"
        },
        {
            "data": "roles"
        },
        {
            "data": "details",
            "render": function(data, type, row) {
                return '<a class="user-details" href="#" id="user_' + row.id + '"><i class="fa fa-2x fa-info-circle" aria-hidden="true"></i></a>';
            },
        }
    ],
    rowCallback: function(row, data) {},
    filter: false,
    info: false,
    ordering: false,
    processing: true,
    retrieve: true,
    bPaginate: true,
    language: {
        emptyTable: "Nothing to show here"
    }
});

$('.subs-card-body').hide();
$('.tenants-card-body').hide();
$('.users-card-body').hide();


$("#fetch-subscriptions").on("click", {
    type: 'subscriptions'
}, listItems);
$("#fetch-tenants").on("click", {
    type: 'tenants'
}, listItems);
$("#fetch-users").on("click", {
    type: 'users'
}, listItems);

function listItems(event) {
    var overlayId = "subscriptions-overlay";
    var endpoint = "/api/subscriptions";
    var table = subscriptionsTable;
    if (event.data.type === 'users') {
        overlayId = "users-overlay";
        endpoint = "/api/users";
        const reqTenantName = $('#tenantName').val();
        if (reqTenantName != '') {
            endpoint = "/api/users?tenantName=" + reqTenantName;
        }
        table = usersTable;
        $('.users-card-body').show();
        $('#users-card-toggle i').removeClass('fa-plus');
        $('#users-card-toggle i').addClass('fa-minus');
    } else if (event.data.type === 'tenants') {
        overlayId = "tenants-overlay";
        endpoint = "/api/tenants";
        table = tenantsTable;
        $('.tenants-card-body').show();
        $('#tenants-card-toggle i').removeClass('fa-plus');
        $('#tenants-card-toggle i').addClass('fa-minus');
    } else if (event.data.type === 'subscriptions') {
        $('.subs-card-body').show();
        $('#subs-card-toggle i').removeClass('fa-plus');
        $('#subs-card-toggle i').addClass('fa-minus');
    }

    document.getElementById(overlayId).style.display = "flex";

    $.get({
        url: endpoint,
        contentType: 'application/json',
        headers: getAuthHeader(),
        success: function(res) {
            document.getElementById(overlayId).style.display = "none";
            if (res.status === 200) {
                table.clear().draw();
                table.rows.add(res.result).draw();
            } else {
                table.language.emptyTable = "<strong class='text-danger'>Error: Something went wrong</strong>";
                table.draw();
            }
        },
        error: function(xhr, status, error) {
            document.getElementById(overlayId).style.display = "none";
            if (xhr.status === 401) {
                table.context[0].oLanguage.sEmptyTable = "<strong class='text-danger'>401: Authenticate first before trying this operation</strong>";
                table.clear().draw();
            } else if (xhr.status === 403) {
                table.context[0].oLanguage.sEmptyTable = "<strong class='text-danger'>403: Forbidden</strong>";
                table.clear().draw();
            } else {
                table.context[0].oLanguage.sEmptyTable = "<strong class='text-danger'>Error: Something went wrong</strong>";
                table.clear().draw();
            }
        }
    })
}

$(document).ready(function() {
    $(document).on('click', '.user-details', function() {
        var userid = $(this)[0].id.split("_")[1];
        console.log(userid);
        //console.log($(this));
        // AJAX request
        $.ajax({
            url: '/api/users/' + userid,
            type: 'get',
            headers: getAuthHeader(),
            success: function(response) {
                // Add response in Modal body
                $('#userDetailsModalLabel').html(response.result.name)
                //$('.modal-body').html("response.result");
                $('#user-detail-username').html(response.result.username)
                $('#user-detail-email').html(response.result.email)
                $('#user-detail-roles').html(response.result.roles.join(', '))
                //$('#user-detail-teams').html(response.result.teams.join(', '))


                // Display Modal
                $('#userDetailsModal').modal('show');
            }
        });
    });
});


$(function() {
    $('#generate-token').click(function(e) {
        e.preventDefault();
        document.getElementById("auth-overlay").style.display = "flex";
        subscriptionsTable.clear().draw();
        $('#auth-error').text("");

        var authenticationForm = {}
        authenticationForm["username"] = $("#username").val();
        authenticationForm["password"] = $("#password").val();

        $.post({
            url: '/api/token/generate-token',
            data: JSON.stringify(authenticationForm),
            contentType: 'application/json',
            success: function(res) {
                document.getElementById("auth-overlay").style.display = "none";
                if (res.status === 200) {
                    $('#authUsername').text(res.result.username);
                    $('#authEmail').text(res.result.email);
                    $('#authTenant').text(res.result.tenant);
                    $('#authRoles').text(res.result.roles);
                    $('#authTrams').text(res.result.teams);
                    $('#authToken').text(res.result.token);
                } else {
                    alert("Invalid response status")
                }
            },
            error: function(xhr, status, error) {
                document.getElementById("auth-overlay").style.display = "none";
                if (xhr.status === 401) {
                    //alert('Invalid Username or Password')
                    $('.toast').toast('show');
                    $('#auth-error').html("<strong class='text-danger'>Invalid Username or Password</strong>")
                } else {
                    //alert('Invalid response')
                    $('#auth-error').html("<strong class='text-danger'>Invalid Response</strong>")
                }
            }
        })
    });
});

function updateSubscription(id, t) {
    if (t.is(':checked')) {
        alert('active ' + id)
    } else {
        alert('inactive ' + id)
    }
};

function updateTenant(id, t) {
    if (t.is(':checked')) {
        alert('active ' + id)
    } else {
        alert('inactive ' + id)
    }
};

function getAuthHeader() {
    var authHeader = {
        'dummy': 'dummy'
    };
    console.log($('#authToken').text())
    if ($('#authToken').text() != '') {
        authHeader = {
            'Authorization': 'Bearer ' + $('#authToken').text()
        }
    }
    return authHeader;
}

$(document).ready(function() {
    $(document).on('click', '.card-toggle', function() {
        var id = $(this)[0].id;
        var body = null;
        var toggle = null;
        if (id === 'auth-card-toggle') {
            body = $('.auth-card-body');
            toggle = $('#auth-card-toggle i');
        }else if(id === 'subs-card-toggle'){
            body = $('.subs-card-body');
            toggle = $('#subs-card-toggle i');
        }else if(id === 'tenants-card-toggle'){
            body = $('.tenants-card-body');
            toggle = $('#tenants-card-toggle i');
        }else if(id === 'users-card-toggle'){
            body = $('.users-card-body');
            toggle = $('#users-card-toggle i');
        }

        if (body.is(":visible")) {
            toggle.removeClass('fa-minus');
            toggle.addClass('fa-plus');
        } else {
            toggle.removeClass('fa-plus');
            toggle.addClass('fa-minus');
        }
        body.toggle();
    });
});