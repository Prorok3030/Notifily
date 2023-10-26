function validation(form){

    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();

    if (dd < 10){
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }

    today = yyyy + '-' + mm + '-' +dd;
    document.getElementById("date_entrance").setAttribute('max', today);

    function removeError(input){
        const parent = input.parentNode;
        if(parent.classList.contains('error')){
            parent.querySelector('.error-label').remove()
            parent.classList.remove('error')
        }
    }

    function createError(input, text){
        const parent = input.parentNode;
        const errorLabel = document.createElement('label')
        errorLabel.classList.add('error-label')
        errorLabel.textContent = text
        parent.append(errorLabel);
        parent.classList.add('error')
    }

    let result = true;

    form.querySelectorAll('.js-input').forEach(input => {
        removeError(input)
        if(input.value==""){
            createError(input, 'Поле не заполнено')
            result=false;
        }
        else{
            if(input.value !== input.value.trim()){
                createError(input, 'Введены незначащие пробелы')
                result=false;
            }
        }
    });

    return result;
}

document.getElementById('form').addEventListener('submit', function(event) {

    if(!validation(this)){
        event.preventDefault();
    }

})