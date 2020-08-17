
var listBooksConfig = "http://localhost:8080/ServiceSystem/resources/library/listbooks/html";
var getBookConfig = "http://localhost:8080/ServiceSystem/resources/library/displaybook?id=";
var addBookConfig = "http://localhost:8080/ServiceSystem/resources/library/addbook";
var updateBookConfig = "http://localhost:8080/ServiceSystem/resources/library/updatebook";
var deleteBookConfig = "http://localhost:8080/ServiceSystem/resources/library/deletebook/";

function getBooks()
{
	var request = new XMLHttpRequest();
	request.open('GET', listBooksConfig, true);
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	request.send();
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}
function getBookById()
{
	var request = new XMLHttpRequest();
	request.open('GET', getBookConfig+document.getElementById(2).value, true);
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	request.send();
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}

function addBook()
{
	var request = new XMLHttpRequest();
	request.open('POST', addBookConfig, true);
	request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	var data = "";
	data+='title='+ document.getElementById("t").value;
	data+='&description='+ document.getElementById("d").value;
	data+='&isbn='+ document.getElementById("i").value;
	data+='&author='+ document.getElementById("a").value;
	data+='&publisher='+ document.getElementById("p").value;
	request.send(data);
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
		
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}

function updateBook()
{
	var request = new XMLHttpRequest();
	request.open('PUT', updateBookConfig, true);
	request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	var data = "";
	data+='id='+ document.getElementById("iupdate").value;
	data+='&Attribute='+ document.getElementById("attribute").value;
	data+='&NewValue='+ document.getElementById("newval").value;
	request.send(data);
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}

function deleteBook()
{
	var request = new XMLHttpRequest();
	request.open('DELETE', deleteBookConfig+document.getElementById(3).value, true);
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	request.send();
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}

function getLoans()
{
	
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: 'soap:listAllLoans',
	data: {
		
	},


	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function getLoan()
{
	var id = document.getElementById("2").value;
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:getALoan",
	data: {
		LoanId: id
	},


	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function getMembers()
{
	
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: 'soap:listMembers',
	data: {
		
	},


	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function getMember(){
	var id = document.getElementById("2").value;
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:getMemberById",
	data: {
		arg0: id
	},

	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function addLoan()
{
	var ids = [];
	//check if book exists
	var request = new XMLHttpRequest();
	request.open('GET',"http://localhost:8080/ServiceSystem/resources/library/listbooks/json" , false);
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	request.send();
	
	function processRequest(e) {
    if(request.readyState == 4 && request.status == 200) 
	{
		var json = JSON.parse(request.responseText);
		for(var i =0; i <json.length; i++)
		{
			ids.push(json[i].id);
		}
		
    }
	else
	{
		document.getElementById(1).innerHTML  ="error";
	}
}
	
	
	var bookId = document.getElementById("b").value;
	var memberId = document.getElementById("m").value;
	var takeoutDate = document.getElementById("t").value;
	var returnDate = document.getElementById("r").value;
	
	var intId = parseInt(bookId,10);
	if(ids.indexOf(intId)==-1)
	{
		document.getElementById(1).innerHTML = "This book doesn't exist" ;
		return;
	}
	
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:createALoan",
	data: {
		arg0: bookId,
		arg1: memberId,
		arg2: takeoutDate,
		arg3: returnDate
	},

	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function deleteLoan()
{
	var loanId = document.getElementById("3").value;
	
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:deleteLoan",
	data: {
		arg0: loanId
	},

	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function updateLoan()
{
	var loanId = document.getElementById("4").value;
	var tod = document.getElementById("5").value;
	var rd = document.getElementById("6").value;
	
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:updateLoan",
	data: {
		arg0: loanId,
		arg1: tod,
		arg2: rd
	},

	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function addMember()
{
	var fn = document.getElementById("f").value;
	var ln = document.getElementById("l").value;
	var contact = document.getElementById("c").value;
	
	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:AddMember",
	data: {
		arg0: fn,
		arg1: ln,
		arg2: contact
	},

	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function deleteMember()
{
	var id = document.getElementById("4").value;

	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:deleteMember",
	data: {
		arg0: id
	},

	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}

function updateMember()
{
	var id = document.getElementById("5").value;
	var option = document.getElementById("6").value;
	var newVal = document.getElementById("7").value;

	$.soap({
	url: 'http://localhost:8888/ws/LoanSystemws/',
	namespaceQualifier: 'soap',
	namespaceURL: 'http://soap/',
	method: "soap:updateMember",
	data: {
		arg0: id,
		arg1: option,
		arg2: newVal
	},

	success: function (soapResponse) {
		document.getElementById(1).innerHTML = soapResponse.toString();

	},
	error: function (SOAPResponse) {
		document.getElementById(1).innerHTML = "Service unavailable";
	}
});
}
