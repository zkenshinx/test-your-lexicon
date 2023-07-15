const startButton = document.querySelector('.start-btn')
const popupInfo = document.querySelector('.popup-info')
const exitButton = document.querySelector('.exit-btn')
const playButton = document.querySelector('.play-btn')
const main = document.querySelector('main')
const gameDiv = document.querySelector('.game')
const entryPage = document.querySelector('.entry-page')
const navbar = document.querySelector('.navbar')
const gamePlayCenterPanel = document.querySelector('.gameplay-center-panel')
const resultBox = document.querySelector('.result-box')

const nextButton = document.querySelector('.next-btn')
const finishButton = document.querySelector('.finish-btn')
const tryAgainButton = document.querySelector('.try-again-btn')
const homeButton = document.querySelector('.home-btn')

const translateFromEl = document.querySelector(".translate-from-select")
const translateToEl = document.querySelector(".translate-to-select")
const optionsCountEl = document.querySelector(".options-count-input")
const stepsCountEl = document.querySelector(".steps-count-input")
const stepTimeEl = document.querySelector(".step-time-input")

const scoreShow = document.querySelector('.score-shower')
const languageToLanguage = document.querySelector('.language-to-language')
const questionsNumEl = document.querySelector('.questions-num')
const resultScore = document.querySelector('.result-score')
const timer = document.querySelector('.timer')

fillLanguages()
fillConfiguration()
showHeader()

startButton.addEventListener('click', function() {
    popupInfo.classList.add('active')
    main.classList.add('active')
});

exitButton.addEventListener('click', function() {
    popupInfo.classList.remove('active')
    main.classList.remove('active')
});

playButton.addEventListener('click', function() {
    popupInfo.classList.remove('active')
    main.classList.remove('active')


    let data = {
        translateFrom: translateFromEl.value,
        translateTo: translateToEl.value ,
        answerCount: optionsCountEl.value,
        numberOfSteps: stepsCountEl.value,
        stepTime: stepTimeEl.value
    }
    fetch("/api/games/configuration", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then(response => {
        return response.json()
    }).then(json => {
        configuration = json
        startGame()
    })
})

finishButton.addEventListener('click', function() {
    if (!finishButton.classList.contains('active')) return
    fetch(`/api/games/${game_id}/end`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({})
    }).then(r => r)
    gamePlayCenterPanel.style.display = "none"
    resultBox.style.display = "block"
    resultScore.innerHTML = `You got ${correctlyAnswered} / ${configuration["numberOfSteps"]}`

})

nextButton.addEventListener('click', function() {
    if (!nextButton.classList.contains('active')) return
    nextButton.classList.remove('active')
    finishButton.classList.remove('active')
    nextStep()
})

homeButton.addEventListener('click', function() {
    entryPage.style.display = "block"
    resultBox.style.display = "none"
    gameDiv.style.display = "none"
    gamePlayCenterPanel.style.display = "none"
})

tryAgainButton.addEventListener('click', function() {
    startGame()
})

function showHeader() {
    fetch("/api/authentication")
        .then(response => {
            return response.json()
        }).then(json => {
            if (json["isAuthenticated"]) {
                navbar.innerHTML += `<a href="/">Info</a>`
                navbar.innerHTML += `<a href="/#" onclick="logout()">Logout</a>`
            } else {
                navbar.innerHTML += `<a href="/login">Login</a>`
                navbar.innerHTML += `<a href="/signup">Register</a>`
            }
        })
}

function logout() {
    fetch("/api/logout").then(r => r.json())
    setTimeout(() => {
        window.location.replace("/")
    }, 100)
}

function startGame() {
    entryPage.style.display = "none"
    resultBox.style.display = "none"
    gameDiv.style.display = "block"
    gamePlayCenterPanel.style.display = "block"

    fetch("/api/games", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({})
    }).then(response => {
        return response.json()
    }).then(json => {
        game_id = json["gameId"]
        console.log(game_id)
        play()
    })
}

let configuration
let correctlyAnswered
let stepsAnsweredSoFar
let game_id
let activeCountDownInterval
let guessTime

function initializeVariables() {
    stepsAnsweredSoFar = 0
    correctlyAnswered = 0
}

function fillLanguages() {
    fetch("/api/games/supported-languages")
        .then(response => {
            return response.json()
        }).then(json => {
        json["languages"].forEach((lang) => {
            const optionFrom = document.createElement("option");
            optionFrom.text = lang
            translateFromEl.add(optionFrom)

            const optionTo = document.createElement("option");
            optionTo.text = lang
            translateToEl.add(optionTo)
        })
    });
}

function fillConfiguration() {
    fetch("/api/games/configuration")
        .then(response => {
            return response.json()
        }).then(json => {
        configuration = json
        translateFromEl.value = json["translateFrom"]
        translateToEl.value = json["translateTo"]
        optionsCountEl.value = json["answerCount"]
        stepsCountEl.value = json["numberOfSteps"]
        stepTimeEl.value = json["stepTime"]
    });
}
function play() {
    initializeVariables()
    showTranslationLanguages()
    nextStep()
}

function initTimer() {
    timer.innerHTML = configuration["stepTime"]
    activeCountDownInterval = setInterval(function() {
        let time = parseInt(timer.innerHTML) - 1
        timer.innerHTML = time.toString()
        if (time === 0) {
            clearInterval(activeCountDownInterval)
            getSolution()
        }
    }, 1000);
}

function getSolution() {
    fetch(`/api/games/${game_id}/solution`)
        .then(response => {
            return response.json()
        }).then(json => {
        showIncorrectAnswer(json["answer"])
        endStep()
    })
}

function showTranslationLanguages() {
    function capitalizeFirstLetter(str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
    let from = capitalizeFirstLetter(configuration["translateFrom"])
    let to = capitalizeFirstLetter(configuration["translateTo"])
    languageToLanguage.innerText = `${from} ➜ ${to}`
}

function showScore() {
    scoreShow.innerText = `Score: ${correctlyAnswered} / ${configuration["numberOfSteps"]}`
}

function showQuestionsNumAsked() {
    questionsNumEl.innerHTML = `${stepsAnsweredSoFar} out of ${configuration["numberOfSteps"]} Questions`
}

function nextStep() {
    guessTime = true
    stepsAnsweredSoFar += 1
    initTimer()
    showScore()
    showNextOrFinishButton()
    showQuestionsNumAsked()
    fetch(`/api/games/${game_id}/step`)
        .then(response => {
            return response.json()
        }).then(json => {
        let question = json["question"]["word"]
        let options = shuffleArray(json["question"]["answerOptions"])

        document.querySelector(".question-text").innerText = question
        let optionsDiv = document.querySelector(".option-list")
        optionsDiv.innerHTML = ''
        for (let i = 0; i < options.length; ++i) {
            optionsDiv.innerHTML += `<button class="option" onclick="checkCorrect(this)">${options[i]}</button>`
        }
    })
}

function showNextOrFinishButton() {
    nextButton.classList.remove('active')
    finishButton.classList.remove('active')
    if (stepsAnsweredSoFar === configuration["numberOfSteps"]) {
        nextButton.style.display = "none"
        finishButton.style.display = "block"
    } else {
        nextButton.style.display = "block"
        finishButton.style.display = "none"
    }
}

function checkCorrect(userAnswerDiv) {
    if (!guessTime) return
    clearInterval(activeCountDownInterval)
    let data = {
        answer: userAnswerDiv.innerText
    }
    fetch(`/api/games/${game_id}/answer`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then(response => {
        return response.json()
    }).then(json => {
        checkUserAnswer(json["userAnswer"], json["correctAnswer"])
        endStep()
    })
}

function checkUserAnswer(userAnswer, correctAnswer) {
    if (userAnswer === correctAnswer) {
        userCorrectlyAnswered(correctAnswer)
    } else {
        showIncorrectAnswer(userAnswer)
        showCorrectAnswer(correctAnswer)
    }
}

function userCorrectlyAnswered(correctAnswer) {
    correctlyAnswered += 1
    showScore()
    showCorrectAnswer(correctAnswer)
}

function showIncorrectAnswer(incorrectAnswer) {
    document.querySelectorAll('.option').forEach((el) => {
        if (el.innerText === incorrectAnswer) {
            el.classList.add("incorrect")
        }
    })
}

function showCorrectAnswer(correctAnswer) {
    document.querySelectorAll('.option').forEach((el) => {
        if (el.innerText === correctAnswer) {
            el.classList.add("correct")
        }
    })
}

function endStep() {
    nextButton.classList.add('active')
    finishButton.classList.add('active')
    guessTime = false
    clearInterval(activeCountDownInterval)
}

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}
