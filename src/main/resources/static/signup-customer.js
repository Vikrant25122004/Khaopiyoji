document.addEventListener("DOMContentLoaded", function () {
    const signupForm = document.getElementById('signupForm');
    const imageFileInput = document.getElementById('imageFile');
    const customerUsernameInput = document.getElementById('customerusername');
    const customerNameInput = document.getElementById('customername');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password'); // Ensure this matches the HTML id
    const addressInput = document.getElementById('address');

    signupForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData();

        const customer = {
            customerusername: customerUsernameInput.value,
            customername: customerNameInput.value,
            customeremail: emailInput.value,
            customerpassword: passwordInput.value, // Include the password value
            customeraddress: addressInput.value
        };

        console.log(customer); // Debugging: Check the customer object

        formData.append('customer', new Blob([JSON.stringify(customer)], { type: 'application/json' }));

        const imageFile = imageFileInput.files[0];
        const MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

        if (imageFile && imageFile.size > MAX_FILE_SIZE) {
            alert('The selected file exceeds the maximum allowed size of 10MB.');
            return;
        }

        if (imageFile) {
            formData.append('imageFile', imageFile);
        }

        // Debugging: Log FormData content
        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }

        fetch('http://localhost:8080/public/create-customer', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (response.ok) {
                alert('Customer registered successfully!');
                signupForm.reset();
                const profilePicture = document.querySelector('.profile-picture img');
                if (profilePicture) {
                    profilePicture.src = '';
                }
            } else {
                alert('Error: Failed to register customer. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error during form submission:', error);
            alert('An error occurred while submitting the form. Please try again.');
        });
    });
});