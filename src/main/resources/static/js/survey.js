var questions = [[${questions}]];
    $('form').on('submit', function(event) {
        event.preventDefault();
        //$('#hiddenInput').val(someVariable); //perform some operations
        alert(questions);
        //this.submit(); //now submit the form
    });