const form = document.getElementById('form');

function validate() {
    const kword = document.getElementById('kword');
    const loc = document.getElementById('loc');
    const dist = document.getElementById('dist');
    const validityState_kword = kword.validity;
    const validityState_loc = loc.validity;
    const validityState_dist = dist.validity;
    
    if (validityState_kword.valueMissing) {
        kword.setCustomValidity('Please fill out this field.');
        kword.reportValidity();
    }else if(validityState_loc.valueMissing){
        loc.setCustomValidity('Please fill out this field.');
        loc.reportValidity();
    }else if(validityState_dist.rangeOverflow){
        dist.setCustomValidity('Enter number between 1-24');
        dist.reportValidity();
    }else if(validityState_dist.rangeUnderflow){
        dist.setCustomValidity('Enter number between 1-24');
        dist.reportValidity();
    }

    if(validityState_kword.valueMissing == false && validityState_loc.valueMissing == false && validityState_dist.rangeOverflow == false && validityState_dist.rangeUnderflow == false){
        get_form_data();
    }
}

form.addEventListener('reset', function(event){
    document.getElementById('table_div').innerHTML = "";
    document.getElementById('infocard_div').innerHTML = "";
    enableDisableLocation(this)
})

function enableDisableLocation(chk_box){
    var txtlocation = document.getElementById("loc");
    txtlocation.value = "";
    txtlocation.disabled = chk_box.checked ? true : false;
    if(!txtlocation.disabled){
        txtlocation.focus();
    }
}   
    
function get_form_data(){
    console.log(window.location.href)

    var keyword = document.getElementById("kword").value;
    var category = document.getElementById("cat").value;
    if(category == 'default'){
        category = 'all'
    }
    var distance = document.getElementById("dist").value ? document.getElementById("dist").value : 10;
    distance = distance*(1609.344);
    var location = document.getElementById("loc").value;

    if(document.getElementById("check").checked){

        var request = new XMLHttpRequest();
        request.open("GET", "https://ipinfo.io/json?token=7b827d7a0f6858", true);

        request.onreadystatechange = function() {
            if (request.readyState == 4 && request.status == 200) 
            {
                search_results = JSON.parse(this.responseText);
                lat = search_results['loc'].split(",")[0]
                lng = search_results['loc'].split(",")[1]
                get_search_data(category, keyword, distance, lat, lng);

            }
        };
        request.send();
    } else {    

        location = location.replaceAll(' ', '+');

        var googlekey = 'AIzaSyBLEkKp9FHS7iECybzv9AesVs-fuoFXlIE';

        var xhttp = new XMLHttpRequest();
        xhttp.open("GET", "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "&key=" + googlekey, true);

        xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            text = xhttp.responseText;
            var jsonResponse = JSON.parse(text)
            try{
                var geometry = jsonResponse['results'][0]['geometry'];
                var lat = geometry['location']['lat']
                var lng = geometry['location']['lng']
                get_search_data(category, keyword, distance, lat, lng);
            }catch(err){
                document.getElementById('table_div').innerHTML = `
                    <div style="text-align:center; background-color:white; width:1000px;margin-left: auto; margin-right: auto;">Error occured: Enter correct location</div>
                `
            }
        }
        };
        xhttp.send();
    }
}
    
function get_search_data(option, kword, dist, lat, lng){

    var request = new XMLHttpRequest();
    request.open("GET", "http://127.0.0.1:5000" + "/" + "search?keyword=" + kword + "&category=" + option + "&distance=" + dist + "&latitude=" + lat + "&longitude=" + lng, true);
    
    request.onreadystatechange = function() {
        if (request.readyState == 4 && request.status == 200) 
        {
            search_results = JSON.parse(this.responseText);
            tableInfo(search_results['businesses'])
        }
    };
    request.send();
}
    
function tableInfo(info){
    var out = "";
    var i = 1;

    for(var business of info){
        out += `
            <tr>
            <td>${i}</td>
            <td><img src='${business['image_url']}'> </td>
            <td onclick = "getMoreInfo('${business['id']}')"><a href="#infocard" style="text-decoration: none;">${business['name']}</a></td>
            <td>${business['rating']}</td>
            <td>${(business['distance']/1609.344).toFixed(2)}</td>
            </tr>
        `;
 
        i = i + 1;
    }

    if(out==""){
        document.getElementById('table_div').innerHTML = `
            <div style="text-align:center; background-color:white; width:1000px;margin-left: auto; margin-right: auto;">No record have been found</div>
        `
    }
    else{
        document.getElementById('table_div').innerHTML = `
                                                            <table id="myTable">
                                                            <thead id="table-head">
                                                            <tr id = 'myTr'>
                                                                <th style="width : 30px";>No.</th>
                                                                <th style="width : 100px";>Image</th>
                                                                <th onclick="sortTable(2)" style="width : 620px"; class="business_name">Business Name</th>
                                                                <th onclick="sortTable(3)" style="width : 175px"; class="business_name">Rating</th>
                                                                <th onclick="sortTable(4)" style="width : 175px"; class="business_name">Distance (miles)</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody id="table-body">
                                                            ${out}
                                                            </tbody>
                                                            </table>
                                                        `
    }
    window.location.href = "#table_div";
}

function getMoreInfo(id){

    var request = new XMLHttpRequest();
    request.open("GET", "http://127.0.0.1:5000" + "/" + "moreinfo?id=" + id, true);
    request.onreadystatechange = function() {
        if (request.readyState == 4 && request.status == 200) 
        {
            moreinfo = JSON.parse(this.responseText);
            infocard(moreinfo);

        };
    }     
    request.send(); 
}

function infocard(moreinfo){
        var add = '';
        var status;

        if(moreinfo['hours'] != null){
            if(moreinfo['hours'][0]['is_open_now'] == true){
                status = 'Open Now';
            }else{
                status = 'Closed';
            }
        }else{
            status = '';
        }

        for(var i=0;i<moreinfo['location']['display_address'].length;i++){
            add += moreinfo['location']['display_address'][i] + ' '
        }

        var address = add? add : '';
        var phone = moreinfo['display_phone']? moreinfo['display_phone'] : '';
        var price = moreinfo['price']? moreinfo['price'] : '';
        var out_transactions = "";

        for(var i = 0; i<moreinfo['transactions'].length; i++){
            if(i == moreinfo['transactions'].length - 1){
                out_transactions += ` ${moreinfo['transactions'][i].charAt(0).toUpperCase() + moreinfo['transactions'][i].slice(1)}`;
            }
            else{
                out_transactions += `${moreinfo['transactions'][i].charAt(0).toUpperCase() + moreinfo['transactions'][i].slice(1)} |`;
            }
        }
        out_transactions = out_transactions? out_transactions : '';

        var out_categories = "";

        for(var i = 0; i<moreinfo['categories'].length; i++){
            if(i == moreinfo['categories'].length - 1){
                out_categories += `${moreinfo['categories'][i]['title']}`;
            }
            else{
                out_categories += `${moreinfo['categories'][i]['title']} | `;
            }
        }

        out_categories = out_categories? out_categories : '';
        var url = moreinfo['url']

        var out = "";
        var img_text = "";
        var i = 1;
    
        out = 
            `
            <div id="infocard">
                <h4 id="business-name"><b>${moreinfo['name']}</b></h4>
                <hr id="separate">
                
                <div class = "details">
                    <div class = "column">
                        <h3 id="status-header">Status</h3>
                        <div id="status">${status}</div>
                        <h3 id="address-header">Address</h3>
                        <p id="address">${address}</p>
                        <h3 id="transactions-header">Transactions supported</h3>
                        <p id="transactions">${out_transactions}</p>
                        <h3 id="more-header">More info</h3>
                        <p id="more"><a id="more-link" href="${url}" target="_blank">Yelp</a></p>
                    </div>
                
                    <div class = "column">
                        <h3>Category</h3>
                        <p id="category">${out_categories}</p>
                        <h3 id="phone-header">Phone Number</h3>
                        <p id="phone">${phone}</p>
                        <h3 id="price-header">Price</h3>
                        <p id="price">${price}</p>
                    </div> 
                </div>`

        var img_text = "";
        for(var i = 0; i<moreinfo['photos'].length; i++){
            img_text += `
                        <div class="photo-container">
                            <img class="img-class" src="${moreinfo['photos'][i]}">
                            <p>Photo ${i+1}</p>
                        </div>
                        `
        }

        document.getElementById('infocard_div').innerHTML = out + `<div class="pics">${img_text}</div>`;

        if(address == ''){
            document.getElementById("address-header").setAttribute("hidden", true);   
        }

        if(phone == ''){
            document.getElementById("phone-header").setAttribute("hidden", true);
        }

        if(price == ''){
            document.getElementById("price-header").setAttribute("hidden", true);
        }
       
        if(out_transactions == ''){
            document.getElementById("transactions-header").setAttribute("hidden", true);
        }

        if(status == ''){
            document.getElementById("status-header").setAttribute("hidden", true);
            document.getElementById("status").setAttribute("hidden", true);            
        }

        if(status == 'Closed'){

            document.getElementById("status").setAttribute("style", "background-color:red");
        }
    
    window.location.href = "#infocard";
}
    

// The below code is taken from w3schools and modified. 

function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("myTable");
    switching = true;
    //Set the sorting direction to ascending:
    dir = "asc"; 
    /*Make a loop that will continue until
    no switching has been done:*/
    while (switching) {
        //start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /*Loop through all table rows (except the
        first, which contains table headers):*/
        for (i = 1; i < (rows.length - 1); i++) {
        //start by saying there should be no switching:
        shouldSwitch = false;
        /*Get the two elements you want to compare,
        one from current row and one from the next:*/
        x = rows[i].getElementsByTagName("TD")[n];
        y = rows[i + 1].getElementsByTagName("TD")[n];
        
        /*check if the two rows should switch place,
        based on the direction, asc or desc:*/
        if (typeof(x.innerHTML) == 'number' && Number(x.innerHTML) > Number(y.innerHTML)) {
            //if so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
        }
        else if (dir == "asc") {
            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
            //if so, mark as a switch and break the loop:
            shouldSwitch= true;
            break;
            }
        } else if (dir == "desc") {
            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
            //if so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
            }
        }
        }
        if (shouldSwitch) {
        /*If a switch has been marked, make the switch
        and mark that a switch has been done:*/
        // $(rows[i]).find('td:first').text(i + 1);
        // console.log(rows[i].cells[0])
        rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);    
        // rows[i].parentNode.insertBefore(rows[i + 1].cells[0], rows[i].cells[0]);                         
        switching = true;
        //Each time a switch is done, increase this count by 1:
        switchcount ++;      
        } else {
        /*If no switching has been done AND the direction is "asc",
        set the direction to "desc" and run the while loop again.*/
        if (switchcount == 0 && dir == "asc") {
            dir = "desc";
            switching = true;
        }
        }
    }

    for(var i=1; i < rows.length; i++){
        rows[i].cells[0].innerHTML = i; 
    }

}
