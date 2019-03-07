window.onload= function(){

    document.getElementById("button")
    .addEventListener("click", flashcards);

}
console.log("working1");
function flashcards(){
    console.log("Working2");

    let xhttp = new XMLHttpRequest;
    let incorrect = document.getElementById("incorrect");
    let user = document.getElementById("user").value;
    let pass = document.getElementById("pass").value;
    xhttp.onreadystatechange = function(){

        if(xhttp.readyState == 4 && xhttp.status == 200){
            let info = JSON.parse(xhttp.responseText);
            //setValues(info);
            console.log("working3");
            if(pass != info.login[0].pass || user != info.login[0].user){
                incorrect.innerHTML = "Incorrect Username and/or Password";
                console.log("Working4");
            }
            else{
                console.log("Working5");
                /* window.location.assign("FlashcardsHTML.html"); */
                window.location.assign("AboutSA.HTML");
               
            }
        }
    }
    xhttp.open("GET","https://api.myjson.com/bins/15koac");
    xhttp.send();

}



