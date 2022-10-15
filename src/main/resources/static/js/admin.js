surveysTable = $("#surveys-table").DataTable({
    data: [],
    columns: [{
            "data": "id"
        },
        {
            "data": "name"
        },
        {
            "data": "status",
            "render": function(data, type, row) {
                switch (row.status) {
                    case 'Draft':
                        return '<span class="badge bg-secondary">Draft</span>';
                        break;
                    case 'Published':
                        return '<span class="badge bg-warning text-dark">Published</span>';
                        break;
                    case 'Auto Complete':
                        return '<span class="badge bg-success">Auto Complete</span>';
                        break;
                    case 'Marked Complete':
                        return '<span class="badge bg-success">Marked Complete</span>';
                        break;
                    case 'Error':
                        return '<span class="badge bg-danger">Error</span>'
                        break;
                    case 'No Valid Participants':
                        return '<span class="badge bg-danger">No Valid Participants</span>'
                        break;
                    case 'Started':
                        return '<span class="badge bg-info text-dark">Started</span>'
                        break;
                    case 'In Progress':
                        return '<span class="badge bg-info text-dark">Started</span>'
                        break;
                }
            }
        },
        {
            "data": "details",
            "render": function(data, type, row) {
                return '<a class="survey-details" href="#" id="survey_' + row.id + '"><i class="fa fa-2x fa-info-circle" aria-hidden="true"></i></a>';
            },
        },
        {
            "data": "update",
            "render": function(data, type, row) {
                if (row.status === 'Draft' || row.status === 'Published') {
                    return '<button class="btn btn-outline-dark text-nowrap float-right w-50 survey_status_update" id="survey_status_update_' + row.id + '_'+row.status+'" type="button">PUBLISH/START</button>';
                } else {
                    return '<button disabled class="btn btn-outline-dark text-nowrap float-right w-50 survey_status_update" id="survey_status_update_' + row.id + '" type="button">PUBLISH/START</button>';
                }
            },
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

surveyEngineTable = $("#survey-engine-table").DataTable({
    data: [],
    columns: [{
            "data": "name"
        },
        {
            "data": "weight"
        },
        {
            "data": "score"
        },
        {
            "data": "questionCount"
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

participantTable = $("#participant-table").DataTable({
    data: [],
    columns: [{
            "data": "email"
        },
        {
            "data": "complete"
        },
        {
            "data": "link",
            "render": function(data, type, row) {
                return '<a class="participant-link" href="' + data + '" target="_blank" id="participant_' + row.id + '"><i class="fa fa-2x fa-external-link" aria-hidden="true"></i></a>';
            }
        }
    ],
    rowCallback: function(row, data) {},
    filter: false,
    info: false,
    ordering: false,
    processing: true,
    retrieve: true,
    bPaginate: false,
    //scrollY: "200px",
    language: {
        emptyTable: "Nothing to show here"
    }
});

questionAnalysisTable = $("#question-analysis-table").DataTable({
    data: [],
    columns: [{
            "data": "question",
            "width": "50%"
        },
        {
            "data": "engine",
            "width": "20%"
        },
        {
            "data": "score",
            "width": "20%",
            "render": function(data, type, row) {
                return ((row.score * 10) / row.responseCount) + '%';
            }
        },
        {
            "data": "responseCount",
            "width": "10%"
        }
    ],
    rowCallback: function(row, data) {},
    filter: false,
    info: false,
    ordering: false,
    processing: true,
    retrieve: true,
    bPaginate: false,
    //scrollY: "200px",
    language: {
        emptyTable: "Nothing to show here"
    }
});

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
$('.persona-card-body').hide();


$("#fetch-subscriptions").on("click", {
    type: 'subscriptions'
}, listItems);
$("#fetch-tenants").on("click", {
    type: 'tenants'
}, listItems);
$("#fetch-users").on("click", {
    type: 'users'
}, listItems);
$("#fetch-surveys").on("click", {
    type: 'surveys'
}, listItems);


function listItems(event) {
    var overlayId = "surveys-overlay";
    var endpoint = "/api/surveys";
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
    } else if (event.data.type === 'surveys') {
        endpoint = "/api/surveys";
        $('.surveys-card-body').show();
        table = surveysTable;
        $('#surveys-card-toggle i').removeClass('fa-plus');
        $('#surveys-card-toggle i').addClass('fa-minus');
    } else if (event.data.type === 'persona') {
        endpoint = "/api/surveys/" + $('#persona-email').val();
        $('.persona-card-body').show();

        $('#persona-card-toggle i').removeClass('fa-plus');
        $('#persona-card-toggle i').addClass('fa-minus');
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
    $(document).on('click', '#surveys-create', function() {
        $('#new-survey-block').empty();
        $.ajax({
            url: 'http://localhost:18082/api/culture/blocks?tenant='+$('#surveyTenant').val(),
            dataType: 'json',
            type: 'GET',
            headers: getAuthHeader(),
            success: function(response) {
                var array = response.result;
                if (array != '') {
                    for (i in array) {
                        $("#new-survey-block").append("<option>" + array[i].code + "</option>");
                    }
                }
            },
            error: function(x, e) {
                console.log(e)
            }
        });
        $('#createSurveyModal').modal('show');
    });
});

$(document).ready(function() {
    $(document).on('click', '#re-calculate-surveys', function() {
        var surveyId = $(this)[0].id.split("_")[1];
        console.log(surveyId);
        //console.log($(this));
        // AJAX request
        $.ajax({
            url: '/api/admin/surveys/update/' + $('#surveyTenant').val(),
            type: 'post',
            headers: getAuthHeader(),
            success: function(response) {
                console.log(response);
            }
        });
    });
});


$(document).ready(function() {
    $(document).on('click', '.survey_status_update', function() {
        var surveyId = $(this)[0].id.split("_")[3];
        var status = $(this)[0].id.split("_")[4];
        console.log("Current Status: ",status);
        const newStatus = status === 'Published' ? 'STARTED' : 'PUBLISHED'
        //console.log($(this));
        // AJAX request
        $.ajax({
            url: '/api/surveys/' + surveyId + '/status-update?status='+newStatus,
            type: 'patch',
            headers: getAuthHeader(),
            success: function(response) {
                console.log(response)
                $("#fetch-surveys").click();
            }
        });
    });
});

$(document).ready(function() {
    $(document).on('click', '.survey-details', function() {
        var surveyId = $(this)[0].id.split("_")[1];
        console.log(surveyId);
        //console.log($(this));
        // AJAX request
        $.ajax({
            url: '/api/surveys/' + surveyId + '/participants',
            type: 'get',
            headers: getAuthHeader(),
            success: function(response) {
                // Add response in Modal body
                $('#surveyDetailsModalLabel').html(response.result.survey_details.name + " by " + response.result.survey_details.publisher)
                //$('#survey-publisher').html(response.result.survey_details.publisher)
                //$('#survey-start').html(response.result.survey_details.startDate)
                //$('#survey-end').html(response.result.survey_details.endDate)
                //$('#survey-status').html(response.result.survey_details.status)
                //$('#survey-block').html(response.result.survey_details.block)
                console.log(response.result.survey_details.engines)
                $('#survey-engines').html(response.result.survey_details.engines)
                const participationPer = Math.round((response.result.survey_details.responseCount * 100.0) / response.result.survey_details.partCount)
                $('#survey-response-percentage').css({
                    '--p': participationPer,
                    'background-color': '#fff',
                    'position': 'relative'
                });
                $('#survey-response-percentage').html(participationPer + '%')
                $('#survey-graph').html('')
                $('#survey-graph').append('<div class="col-md-3"> <div><b>Responses</b></div> <div id="survey-response-percentage" class="pie" style="--p:' + participationPer + ';--b:10px;--c:purple;">' + participationPer + '%</div> </div>')
                response.result.survey_details.engines.forEach(engine => {
                    $('#survey-graph').append('<div class="col-md-3"> <div><b>' + engine.name + '</b></div> <div id="survey-' + engine.name + '" class="pie" style="--p:' + engine.score + ';--b:10px;--c:purple;">' + engine.score + '%</div> </div>')
                })

                participantTable.clear().draw();
                participantTable.rows.add(response.result.participants).draw();

                questionAnalysisTable.clear().draw();
                questionAnalysisTable.rows.add(response.result.survey_details.questions).draw();

                surveyEngineTable.clear().draw();
                surveyEngineTable.rows.add(response.result.survey_details.engines).draw();
                //const
                //$('.modal-body').html("response.result");
                //$('#user-detail-username').html(response.result.username)
                //$('#user-detail-email').html(response.result.email)
                //$('#user-detail-roles').html(response.result.roles.join(', '))
                //$('#user-detail-teams').html(response.result.teams.join(', '))


                // Display Modal
                $('#surveyDetailsModal').modal('show');
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
        'dummy': 'dummy',
        'tenant': $('#surveyTenant').val()
    };
    console.log($('#authToken').text())
    if ($('#authToken').text() != '') {
        authHeader = {
            'Authorization': 'Bearer ' + $('#authToken').text(),
            'tenant': $('#surveyTenant').val()
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
        } else if (id === 'subs-card-toggle') {
            body = $('.subs-card-body');
            toggle = $('#subs-card-toggle i');
        } else if (id === 'tenants-card-toggle') {
            body = $('.tenants-card-body');
            toggle = $('#tenants-card-toggle i');
        } else if (id === 'users-card-toggle') {
            body = $('.users-card-body');
            toggle = $('#users-card-toggle i');
        } else if (id === 'surveys-card-toggle') {
            body = $('.surveys-card-body');
            toggle = $('#surveys-card-toggle i');
        } else if (id === 'persona-card-toggle') {
            body = $('.persona-card-body');
            toggle = $('#persona-card-toggle i');
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

$('#create-survey-form').on('submit', function(event) {
    event.preventDefault();
    //alert($('#new-survey-name').val());
    const request = {
        name: $('#new-survey-name').val(),
        description: $('#new-survey-description').val(),
        startDate: $('#new-survey-start').val(),
        endDate: $('#new-survey-end').val(),
        block: $('#new-survey-block').val(),
        smartSkip: $('#new-survey-smart-skip').attr('checked'),
        publisher: $('#new-survey-publisher').val(),
        participantSource: $("input:radio[name='new-survey-part-source']:checked").val(),
        comment: 'From Admin Tool',
        reminderFrequencyInDays: 0,
        tags: ['adminTool'],
        participants: $('#new-survey-participants').val().split(','),
    }
    console.log($('#new-survey-participants').val())
    console.log($('#new-survey-participants').val().split(','))
    //alert($('#new-survey-publisher').val().split(','))
    $.ajax({
        url: '/api/admin/surveys/' + $('#surveyTenant').val() + "?publisher=" + $('#new-survey-publisher').val(),
        type: 'post',
        contentType: 'application/json',
        data: JSON.stringify(request),
        headers: getAuthHeader(),
        success: function(response) {
            console.log(response);
            $('#createSurveyModal').modal('hide');
            $("#fetch-surveys").click();
        },
        error: function(x, e) {
            console.log("Error: ",x.responseText)
            $('#new-survey-error').html(x.responseText);
        }
    });
    //this.submit(); //now submit the form
});

$('#fetch-persona').on('click', function(event) {
    event.preventDefault();
    $.ajax({
        url: '/api/users/persona/' + $('#persona-tenant').val() + "/" + $('#persona-email').val() +
            "?engine=" + $('#persona-engine').val() + "&frequency=" + $('#persona-frequency').val() +
            "&from=2022-04-20",
        type: 'get',
        contentType: 'application/json',
        headers: getAuthHeader(),
        success: function(response) {
            console.log(response);
            var xValues = [];
            var yValues = [];
            response.result.forEach(s => {
                xValues.push(s.xlabel)
                yValues.push(s.score)
            })
            //xValues.push("END")
            //yValues.push(100)

            console.log(xValues)
            console.log(yValues)

            new Chart("persona-chart", {
                type: "line",
                data: {
                    labels: xValues,
                    datasets: [{
                        fill: false,
                        label: $('#persona-engine').val(),
                        lineTension: 0.3,
                        backgroundColor: "rgba(0,0,255,0.5)",
                        borderColor: "#129da5",
                        pointStyle: ['saurav', 'india', 'odisha', 'karnataka'],
                        data: yValues,
                        pointBackgroundColor: "#129da5"
                    }]
                },
                options: {
                    showTooltips: true,
                    legend: {
                        display: false
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                min: 0,
                                max: 100
                            }
                        }],
                    },
                    plugins: {
                        tooltip: {
                            usePointStyle: true,
                            callbacks: {
                                labelPointStyle: function(context) {
                                    return {
                                        pointStyle: 'triangle',
                                        rotation: 0
                                    };
                                },
                                label: function(context) {
                                    //let label = "Saurav"
                                    return "Saurav";
                                }
                            }
                        }
                    }
                }
            });
        },
        error: function(x, e) {
            console.log("Error: ",e)
            $('#new-survey-error').html(e);
        }
    });
    //this.submit(); //now submit the form
});

$(document).ready(function() {

    $('.manual-participants').hide()
    $('.participant-source-selection').click(function() {
        if ($("input:radio[name='new-survey-part-source']:checked").val() === 'DIRECT_REPORTS') {
            $('.manual-participants').hide('slow')
        } else {
            $('.manual-participants').show('slow')
        }
    })

    $('.survey-start-date').hide()
    $("#new-survey-schedule-start").change(function() {
        if (this.checked) {
            console.log("scheduled start active")
            $('.survey-start-date').show('slow')
        } else {
            console.log("scheduled start inactive")
            $('.survey-start-date').hide('slow')
        }
    });
});