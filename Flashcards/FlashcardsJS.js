function getSelectvalue(){
    var selectedValue = document.getElementById("list").value;
    console.log(selectedValue);
}

function loadJSON(){
    let json = "https://api.myjson.com/bins/1apyw0";
    let requestObject = new XMLHttpRequest();

    requestObject.onreadystatechange = function(){      
        if (requestObject.readyState == 4 && requestObject.status == 200 ){

        var jsonObj = JSON.parse(requestObject.responseText);         

        document.getElementById("q1").innerHTML = jsonObj.questions[0].q1;

        document.getElementById("q2").innerHTML = jsonObj.questions[0].q2;

        document.getElementById("q3").innerHTML = jsonObj.questions[0].q3;

        document.getElementById("q4").innerHTML = jsonObj.questions[0].q4;

        document.getElementById("q5").innerHTML = jsonObj.questions[0].q5;

        document.getElementById("a1").innerHTML = jsonObj.questions[1].a1;

        document.getElementById("a2").innerHTML = jsonObj.questions[1].a2;

        document.getElementById("a3").innerHTML = jsonObj.questions[1].a3;

        document.getElementById("a4").innerHTML = jsonObj.questions[1].a4;

        document.getElementById("a5").innerHTML = jsonObj.questions[1].a5;

    }

  }
  requestObject.open("GET", json, true);

  requestObject.send();


}

function loadJSON1(){
    let json = "https://api.myjson.com/bins/isqm8";
    let requestObject = new XMLHttpRequest();

    requestObject.onreadystatechange = function(){      
        if (requestObject.readyState == 4 && requestObject.status == 200 ){

        var jsonObj = JSON.parse(requestObject.responseText);         

        document.getElementById("q1").innerHTML = jsonObj.questions[0].q1;

        document.getElementById("q2").innerHTML = jsonObj.questions[0].q2;

        document.getElementById("q3").innerHTML = jsonObj.questions[0].q3;

        document.getElementById("q4").innerHTML = jsonObj.questions[0].q4;

        document.getElementById("q5").innerHTML = jsonObj.questions[0].q5;

        document.getElementById("a1").innerHTML = jsonObj.questions[1].a1;

        document.getElementById("a2").innerHTML = jsonObj.questions[1].a2;

        document.getElementById("a3").innerHTML = jsonObj.questions[1].a3;

        document.getElementById("a4").innerHTML = jsonObj.questions[1].a4;

        document.getElementById("a5").innerHTML = jsonObj.questions[1].a5;

    }

  }
  requestObject.open("GET", json, true);

  requestObject.send();


}
