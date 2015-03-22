// JavaScript source code
function GenerateFunction() {
    var http = new XMLHttpRequest();

    http.onreadystatechange = callback;
    http.open("GET", window.location.href + "generate", true);
    http.send();
    function callback() {
        try {
            if (http.readyState === 4) {
                if (http.status === 200) {
                    response = JSON.parse(http.responseText);
                    document.getElementById("outputWrapper").hidden = "";
                    document.getElementById("restName").innerHTML = response[0].Name;
                    document.getElementById("restType").innerHTML = response[0].Type;
                    document.getElementById("activity").innerHTML = response[1].Activity
                    document.getElementById("activity").href = response[1].URL;
                }
                else {
                    alert("Problem getting data from server" + http.status)
                }
            }
        }
        catch (e) {
            alert('Exception!');
        }
    }
}