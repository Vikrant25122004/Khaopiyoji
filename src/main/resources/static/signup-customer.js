// Wait for the document to be fully loaded
document.addEventListener("DOMContentLoaded", function () {

  // Get references to the form and the input elements
  const signupForm = document.getElementById('signupForm');
  const imageFileInput = document.getElementById('imageFile');
  const customerUsernameInput = document.getElementById('customerusername');
  const customerNameInput = document.getElementById('customername');
  const emailInput = document.getElementById('email');
  const passwordInput = document.getElementById('password');
  const addressInput = document.getElementById('address');
  
  // Handle the file selection preview
  imageFileInput.addEventListener('change', function (event) {
      const file = event.target.files[0];
      const reader = new FileReader();

      // Check if a file is selected
      if (file) {
          reader.onload = function () {
              const profilePicture = document.querySelector('.profile-picture img');
              if (profilePicture) {
                  profilePicture.src = reader.result;
              }
          };
          reader.readAsDataURL(file);
      }
  });

  // Form submission handler
  signupForm.addEventListener('submit', function (event) {
      event.preventDefault(); // Prevent default form submission
      
      // Create FormData object to send the form data as multipart/form-data
      const formData = new FormData();

      // Add the values from the input fields
      formData.append('customer.customerusername', customerUsernameInput.value);
      formData.append('customer.customername', customerNameInput.value);
      formData.append('customer.customeremail', emailInput.value);
      formData.append('customer.customerpassword', passwordInput.value);
      formData.append('customer.customeraddress', addressInput.value);

      // Append the selected image file
      const imageFile = imageFileInput.files[0];
      if (imageFile) {
          formData.append('imageFile', imageFile);
      }

      // Send the form data to the backend using Fetch API
      fetch('http://localhost:8080/public/create-customer', {
          method: 'POST',
          body: formData
      })
      .then(response => {
          if (response.ok) {
              alert('Customer registered successfully!');
              // Optionally, redirect to another page or reset the form
              signupForm.reset();
              const profilePicture = document.querySelector('.profile-picture img');
              if (profilePicture) {
                  profilePicture.src = ''; // Reset the profile image preview
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
