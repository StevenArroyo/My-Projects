function loadJSON(){
    let json = "https://api.myjson.com/bins/1am2gg";
    let requestObject = new XMLHttpRequest();

    requestObject.onreadystatechange = function(){      
        if (requestObject.readyState == 4 && requestObject.status == 200 ){

        var jsonObj = JSON.parse(requestObject.responseText);         

        document.getElementById("name").innerHTML = jsonObj.about[0].name;

        document.getElementById("degree").innerHTML = jsonObj.about[0].degree;

        document.getElementById("school").innerHTML = jsonObj.about[0].school;

        document.getElementById("likes").innerHTML = jsonObj.about[0].likes;

        document.getElementById("age").innerHTML = jsonObj.about[0].age;

    }

  }
  requestObject.open("GET", json, true);

  requestObject.send();


}

