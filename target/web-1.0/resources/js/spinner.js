function incrementX(step) {
    const input = document.getElementById('inputForm:xCoord');
    const currentValue = parseFloat(input.value) || 0;
    let newValue = currentValue + step;


    newValue = Math.round(newValue * 2) / 2;

    if (newValue >= -4 && newValue <= 4) {
        input.value = newValue;

        const event = new Event('change', { bubbles: true });
        input.dispatchEvent(event);
    }
}


document.addEventListener('DOMContentLoaded', function() {
    const input = document.getElementById('inputForm:xCoord');
    if (!input.value) {
        input.value = '0';
    }


    input.addEventListener('keydown', function(e) {
        if (e.key === 'ArrowUp') {
            e.preventDefault();
            incrementX(0.5);
        } else if (e.key === 'ArrowDown') {
            e.preventDefault();
            incrementX(-0.5);
        }
    });


    input.addEventListener('change', function() {
        let value = parseFloat(this.value);

        if (isNaN(value)) {
            this.value = '0';
            return;
        }

        value = Math.round(value * 2) / 2;

        value = Math.max(-4, Math.min(4, value));

        this.value = value;
    });
});