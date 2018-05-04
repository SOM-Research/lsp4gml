var graph = new joint.dia.Graph();
var paper = new joint.dia.Paper({
    el: $('#paper'),
    width: 1140,
    height: 600,
    gridSize: 1,
    model: graph
});

var uml = joint.shapes.uml;

mysvg = '<g class="rotatable"><g class="scalable">' +
    '<rect y="0" x="0" height="10" width="20" style="color:#000000;opacity:1;vector-effect:none;fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.25;stroke-opacity:1;"/>' +
    '</g>'+
    '<text id="name" y="30" x="45" style="font-family:sans-serif;fill:#000000;fill-opacity:1;"></text>' + 
    '</g>';

myShape = new joint.shapes.basic.Generic({
    markup: mysvg,
    attrs: {
        image: {
            width: 100,
            height: 100
        },
        '#name' : {
            text : 'A'
        }
    },
    size: {
        width: 100,
        height: 50
    },
    position: {
        x: 500,
        y: 100
    }
});

var nodes = {
    myShape
};
_.each(nodes, function(n) { graph.addCell(n); });

var edges = [ ];
_.each(edges, function(e) { graph.addCell(e); });

function log(msg){
    currentText = $('#notification').val();
    newText = currentText + "\n" + msg;
    $('#notification').val(newText);
    $('#notification').scrollTop($('#notification')[0].scrollHeight);
}

// JSON RPC

//configure
var jrpc = new simple_jsonrpc();

jrpc.toStream = function(_msg){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState != 4) return;
        if (this.responseText == undefined || this.responseText == "") return;
        try {
            JSON.parse(this.responseText);
            jrpc.messageHandler(this.responseText);
        } catch (e){
            console.error(e);
        }
    };
    xhr.open("POST", 'http://localhost:8080', true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(_msg);
};


//calls
iniParams = {
    "processId" : 0,
    "rootUri" : "C:\\Users\\jcanovasi\\git\\lsp\\HelloWorldLanguageServer",
    "capabilities" :  {
        "textDocument" : null,
        "workspace" : null,
        "experimental" : null
    }
};

$('#shutdownButton').attr('disabled', 'disabled');
$('#exitButton').attr('disabled', 'disabled');

function initialize() {
    jrpc.call('initialize', iniParams).then(function (result) {
        log("Initialize message sent");
        console.log(result);
    });
    $('#initializeButton').attr('disabled', 'disabled');
    $('#shutdownButton').removeAttr('disabled');
}

function shutdown() {
    jrpc.call('shutdown', null).then(function (result) {
        log("Shutdown message sent");
        console.log(result);
    });
    $('#shutdownButton').attr('disabled', 'disabled');
    $('#exitButton').removeAttr('disabled');
}

function exit() {
    jrpc.notification('exit', null);
    log("Exit message sent");
    $('#exitButton').attr('disabled', 'disabled');
    $('#initializeButton').removeAttr('disabled');
}

jrpc.on('window/showMessage', 'pass', function(result) {
    log(result.message);
    console.log(result);
});

