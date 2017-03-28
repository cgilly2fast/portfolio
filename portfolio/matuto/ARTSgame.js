// Colby Gilbert
// 11/03/2016

// Script for ARTs game
// DESCRIPTION:	methods to allow for functionality for ARTS game and repitition
// 				definiton: Qs = questions

(function() {
	"use strict"

	var places
	var totalClues;
	var finalQ;
	var correctAns;
	var count;
	var Qset;


	window.onload = function() {
		//	initialize the final question in game and what the correct answer is 
		//	for each question, in a 2D array
		var finalQs = [
			[4, "How many total Artworks?"],
			[6, "How many total Artworks are there?"],
			[5, "How many Artworks are there?"],
			[3, "How many Artworks?"],
			[6, "Whats the total amount of Artworks?"],
			[8, "How many of these Artworks are actually art?"],
			[7, "How many total Artworks did you see?"],
			[5, "How many did you count?"],
			[5, "How many are real Art?"],
			[6, "How many Artworks did you see?"],
			[8, "Whats the total amount of Artworks you saw?"],
			[5, "How many are good Art?"],
			[8, "How many of the artworks did you understand?"],
			[5, "How many did you see?"],
			[6, "How many are not considered art?"],
			[9, "How many are not considered quailty works of art?"],
			[6, "How many are not real Art?"],
			[11, "How many of the works are made by a quailty artist?"],
			[7, "How many of the Artworks reference identity?"],
			[5, "How many Artworks reference memory?"],
			[6, "How many were made by men?"],
			[11, "Whats the total amount of Artworks made by a black artists?"],
			[9, "How many would be better art as fire starter?"],
			[7, "How many will be taken down today?"],
			[7, "How many should be taken down today?"],
			[5, "How many did you like?"],
			[7, "How many of Artworks did you like?"],
			[7, "How many total Artworks spoke to you?"],
			[7, "How many Artworks did you not like?"],
			[7, "How many total Artworks did you like?"],
			[8, "How many total Artworks did you not like?"]
		];

		// initializes the location of all the possible clues in an array
		places = [
			"Room 116",
			"Room 10",
			"Room 9",
			"The Jake",
			"SOACC",
			"The Woodshop",
			"The front stairway",
			"The Unisex Bathroom",
			"Flint's office",
			"The Dark Room",
			"The bulletin board outside the front office",
			"The front office",
			"The Design Hallway",
			"Locker #770",
			"Space 120",
			"The Art Library",
			"Parnassuss",
			"The Equipment Room",
			"The Metal Design Shop",
			"The Printmaking Studio",
			"The DPS"
		];
		Qset = new Set();
		// sets max number of deciving questions can be asked (1-5)
		totalClues = Math.floor((Math.random() * 5) + 1 );
		// alert(totalClues); /*test code*/

		// picks the final question
		var finalQindex = Math.floor(Math.random() * finalQs.length);

		// retreives info  
		finalQ = finalQs[finalQindex][1];
		correctAns = finalQs[finalQindex][0];

		// sets max number of "artworks" that can be stated
		count = correctAns * 2;

		// displays text for front page of app 
		var h1 = document.createElement("h1");
		h1.innerHTML = "Matuto";
		h1.setAttribute("id", "title");
		var title = document.getElementById("main");
		title.appendChild(h1);

		var h4 = document.createElement("h4");
		h4.innerHTML = "A web app for keeping your art awareness sharp";
		h4.setAttribute("id", "demo")
		var subtitle = document.getElementById("main");
		subtitle.appendChild(h4);

		// creates and adds games start button to DOM
		var button = document.createElement("button");
		button.innerHTML = "Start";
		button.setAttribute("id", "button");
		var main = document.getElementById("main");
		main.appendChild(button);
		button.onclick = questionAsker;
	}

	// Function that calls each questin with the correct delay to be readable by user
	function questionAsker() {
		
		displayClue();
		for(var i = 1; i <= totalClues - 1; i++) {
			doScaledTimeout(i);
		}
		setTimeout(finalQuestion, totalClues * 4000);
	 }

	function doScaledTimeout(i) {
		setTimeout(displayClue, i * 4000);
	}

	// 	Input: element iD of a HTML elmenet
	//	Output: clears inner HTML of the element 
	function clearBox(elementID) {
    	document.getElementById(elementID).innerHTML = "";
	}

	//	Output: displays a clue, example: "Room 9 has 12 Artworks"
	function displayClue() {
		clearBox("main");

		// get "main" div element
		var main = document.getElementById("main");
		var h2 = document.createElement("h2");
		h2.setAttribute("id", "text");

		//decides how many artwaorks are in a place
		var innerCount = Math.floor(Math.random() * count);
		// picks a places and if a question has already been picked a new one is picked
		var index = Math.floor(Math.random() * places.length);
		while(Qset.has(index)) {
			index = Math.floor(Math.random() * places.length);
		}
		Qset.add(index);
		// sets question in text element
		if(innerCount == 1) {
			h2.innerHTML = places[index] + " has " + innerCount + " Artwork...";
		} else if(innerCount == 0) {
			h2.innerHTML = places[index] + " has no Artworks...";
		} else {
			h2.innerHTML = places[index] + " has " + innerCount + " Artworks...";
		}

		main.appendChild(h2);	
	}

	//	Output: Displays final question of game
	function finalQuestion() {
		clearBox("main");
		var div1 = document.createElement("div");
		var div2 = document.createElement("div");

		var main = document.getElementById("main");
		//	creates final question
		var h2 = document.createElement("h2");
		h2.setAttribute("id", "text");
		h2.innerHTML = finalQ;
		main.appendChild(h2);
		//	create input feild
		var input = document.createElement("input");
		input.setAttribute("id", "guess");
		div1.appendChild(input)
		document.getElementById("main").appendChild(div1);
	
		//	creates submit button
		var button = document.createElement("button");
		button.innerHTML = "Submit";
		button.setAttribute("id", "button");
		div2.appendChild(button);
		main.appendChild(div2);
		button.onclick = validate;

	}	

	//	Input: onclick of sunbmit button on final question page
	//	Output: alerts user of invaild input, alerts user of wrong 
	//			answer, or alerts user of corret answer
	function validate() {
    	var x, text;

	    // Get the value of the input field with id="numb"
	    x = document.getElementById("guess").value;

	    if(!document.getElementById("demo")) {
	    	var h5 = document.createElement("h5");
			h5.setAttribute("id", "demo");
		} else {
			var h5 = document.getElementById("demo")
		}

	    // If x is Not a Number
	    if (isNaN(x)) {
	        text = "Input not valid";
	        h5.innerHTML = text;
	    } else if(x == "") {
	    	text = "Enter guess before submitting!";
	        h5.innerHTML = text;
    	} else if(x == correctAns) {
        	correctAnswer();
	    } else {
	    	wrongAnswer();
	    }
    	
    	if(!document.getElementById("demo")) {
	    	var validate = document.getElementById("main");
			validate.appendChild(h5);
		}
	}

	//	Input: fuction is only called when user submits a incorrect answer
	//	Output: displays alert of incorrect answer, displays correct answer 
	//			and try again button
	function wrongAnswer() {
		clearBox("main");

		var shitTalking = [
			"Looks like the art gallery isnt for you!",
			"See you back in the classroom.",
			"Maybe another year of tuition will fix you.",
			"Its okay, we all start somewhere.",
			"Come on! Your smarter than that!",
			"I know you can do this.",
			"How many years have you been in school?",
			"Lets try again.. Maybe you just didnt pay attention in class.",
			"Your just bad at tests, your actually really smart!",
			"Ouch! One more time..",
		];

		var h2 = document.createElement("h2");
		h2.innerHTML = "Wrong! " + shitTalking[Math.floor(Math.random() * shitTalking.length)];
		h2.setAttribute("id", "text");
		var text = document.getElementById("main");
		text.appendChild(h2);

		var h4 = document.createElement("h4");
		h4.innerHTML = "Correct Answer: " + correctAns;
		h4.setAttribute("id", "demo")
		var correct = document.getElementById("main");
		correct.appendChild(h4);

		tryAgain();
	}

	//	Input: fuction is only called when user submits a correct answer
	//	Output: displays alert of correct answer and try again button
	function correctAnswer() {
		clearBox("main");

		var h2 = document.createElement("h2");
		h2.innerHTML = "Correct! Your learning a lot nice job!";
		h2.setAttribute("id", "text");
		var text = document.getElementById("main");
		text.appendChild(h2);
		
		tryAgain();
	}

	// Input: Only called when a valid iunput is submitted
	// Output: Creates try again buttton for user to play again
	function tryAgain() {
		var button = document.createElement("button");
		button.innerHTML = "Try Again";
		button.setAttribute("id", "button");
		var main = document.getElementById("main");
		main.appendChild(button);
		button.onclick = window.location.reload.bind(window.location);
	}
}());