let loginButton = document.getElementById("login-button");
let firstNameInput = document.getElementById("first-name-input");
let lastNameInput = document.getElementById("last-name-input");
let emailInput = document.getElementById("email-input");
let passwordInput = document.getElementById("password-input");
let confirmationPasswordInput = document.getElementById("confirmation-password-input")
let badCredentialsEl = document.querySelector(".hidden-message")

loginButton.addEventListener("click", function () {
    let data = {
        firstName: firstNameInput.value,
        lastName: lastNameInput.value,
        email: emailInput.value,
        password: passwordInput.value,
        confirmationPassword: confirmationPasswordInput.value
    }
    fetch("/api/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            document.querySelector(".signup-panel").style.display = "none";
            document.querySelector(".success-panel").style.display = "block";
        }
        return response.json()
    }).then(json => {
        badCredentialsEl.textContent = json.message
        if (!badCredentialsEl.classList.contains("message"))
            badCredentialsEl.classList.add("message")
    })
});
