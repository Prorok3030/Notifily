function validation(form){

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

    form.querySelectorAll('.js-number').forEach(input => {
        removeError(input)
        if(!/^\d+$/.test(input.value) || input.value == "0"){
            createError(input, 'Необходимо ввести положительное число больше нуля')
            result=false;
        }
        if(input.value==""){
            removeError(input)
            createError(input, 'Поле не заполнено')
            result=false;
        }
            if(input.value !== input.value.trim()){
                removeError(input)
                createError(input, 'Введены незначащие пробелы')
                result=false;
            }
    });

    return result;
}

document.getElementById('form').addEventListener('submit', function(event) {

    if(!validation(this)){
        event.preventDefault();
    }

})