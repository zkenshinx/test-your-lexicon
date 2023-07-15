let loginButton = document.getElementById("login-button");
let emailInput = document.getElementById("email-input");
let passwordInput = document.getElementById("password-input");
let badCredentialsEl = document.querySelector(".hidden-message")

loginButton.addEventListener("click", function () {
    let data = {
        email: emailInput.value,
        password: passwordInput.value
    }
    fetch("/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        // redirect to home page
        if (response.ok) {
            window.location.replace("/");
        }
        if (!badCredentialsEl.classList.contains("message")) {
            badCredentialsEl.classList.add("message")
            badCredentialsEl.textContent = "Bad Credentials!"
        } else {
            // If we already have bad credentials showed
            // just make it slightly bigger
            badCredentialsEl.classList.add("enlarged")
            setTimeout(() => {
                badCredentialsEl.classList.remove("enlarged")
            }, 500);
        }
    })
});