function validateForm() {
    const xInput = document.getElementById('inputForm:xCoord');
    const yInput = document.getElementById('inputForm:yCoord');
    const rInput = document.getElementById('inputForm:radius');

    clearErrors();
    let isValid = true;

    if (!xInput?.value) {
        showError(xInput, 'Введите значение X');
        isValid = false;
    } else {
        const x = parseFloat(xInput.value);
        if (isNaN(x)) {
            showError(xInput, 'X должен быть числом');
            isValid = false;
        } else if (x < -4 || x > 4 || (x * 2) % 1 !== 0) {
            showError(xInput, 'X должен быть от -4 до 4 с шагом 0.5');
            isValid = false;
        }
    }

    if (!yInput?.value) {
        showError(yInput, 'Введите значение Y');
        isValid = false;
    } else {
        const y = parseFloat(yInput.value);
        if (isNaN(y)) {
            showError(yInput, 'Y должен быть числом');
            isValid = false;
        } else if (y < -3 || y > 3) {
            showError(yInput, 'Y должен быть от -3 до 3');
            isValid = false;
        }
    }

    if (!rInput?.value) {
        showError(rInput, 'Выберите значение R');
        isValid = false;
    } else {
        const r = parseFloat(rInput.value);
        const validRValues = [1, 1.5, 2, 2.5, 3];
        if (!validRValues.includes(r)) {
            showError(rInput, 'R должен быть одним из значений: 1, 1.5, 2, 2.5, 3');
            isValid = false;
        }
    }

    return isValid;
}

function showError(element, message) {
    if (!element?.parentElement) return;

    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;

    const existingError = element.parentElement.querySelector('.error-message');
    if (existingError) {
        existingError.remove();
    }

    element.parentElement.appendChild(errorDiv);
    element.classList.add('error');
}

function clearErrors() {
    document.querySelectorAll('.error-message').forEach(el => el.remove());
    document.querySelectorAll('.error').forEach(el => el.classList.remove('error'));
}

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('inputForm');
    if (!form) return;

    form.addEventListener('submit', (e) => {
        if (!validateForm()) {
            e.preventDefault();
            return false;
        }
        return true;
    });

    const xInput = document.getElementById('inputForm:xCoord');
    const yInput = document.getElementById('inputForm:yCoord');
    const rInput = document.getElementById('inputForm:radius');

    if (xInput && !xInput.value) xInput.value = '1.0';
    if (yInput && !yInput.value) yInput.value = '1.0';
    if (rInput && !rInput.value) rInput.value = '1.0';
});