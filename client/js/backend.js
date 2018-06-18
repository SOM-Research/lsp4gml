
// Our IRF definition
var irf =
 {
    "rootElement": "Family",
    "namedElementPackage": "family.model.family.impl.NamedElementImpl",
    "implPackage": "family.model.family.impl.",
    "nodes": [
        {
            "id": "1",
            "type": "Family",
            "abstract" : {
                "name" : "F1",
                "lastName" : "Ramirez"
            },
            "concrete" : { "width" : 100, "height" : 50 },
            "diagram": {
                "x" : 50, 
                "y" : 40, 
                "z" : 0
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }           
        },
        {
            "id": "2",
            "type": "Member",
            "abstract": {
                "name" : "M1",
                "firstName" : "Julio",
                "age" : 45,
                "gender" : "male"
            },
            "concrete" : { "width" : 100, "height" : 50 },
            "diagram": {
                "x" : 160, 
                "y" : 40, 
                "z" : 0
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }               
        },
        {
            "id": "3",
            "type": "Member",
            "abstract": {
                "name" : "M2",
                "firstName" : "Margarita",
                "age" : 40,
                "gender" : "female"
            },
            "concrete" : { "width" : 100, "height" : 50 },
            "diagram": {
                "x" : 280, 
                "y" : 40, 
                "z" : 0
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                   
        },
        {
            "id": "4",
            "type": "Member",
            "abstract": {
                "name" : "M3",
                "firstName" : "Hugo",
                "age" : 15,
                "gender" : "male"
            },
            "concrete" : { "width" : 100, "height" : 50 },
            "diagram": {
                "x" : 50, 
                "y" : 110, 
                "z" : 0
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                       
        },
        {
            "id": "5",
            "type": "Member",
            "abstract": {
                "name" : "M4",
                "firstName" : "Adrian",
                "age" : 8,
                "gender" : "male"
            },
            "concrete" : { "width" : 100, "height" : 50 },
            "diagram": {
                "x" : 50, 
                "y" : 220, 
                "z" : 0
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                       
        },
        {
            "id": "6",
            "type": "Member",
            "abstract": {
                "name" : "M5",
                "firstName" : "Sofia",
                "age" : 10,
                "gender" : "female"
            },  
            "concrete" : { "width" : 100, "height" : 50 },        
            "diagram": {
                "x" : 210, 
                "y" : 220, 
                "z" : 0
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }               
        }       
    ],
    "edges": [
     {
            "id": "1",
            "type": "default",
            "origin": "1",
            "target": "2",
            "abstract": {
                "name" : "father",
                "multiplicity" : "1",
                "containment" : "yes"
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                                           
        },
        {
            "id": "2",
            "type": "default",
            "origin": "1",
            "target": "3",
            "abstract": {
                "name" : "mother",
                "multiplicity" : "1",
                "containment" : "yes"
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                                           
        },
        {
            "id": "3",
            "type": "default",
            "origin": "1",
            "target": "4",
            "abstract": {
                "name" : "sons",
                "multiplicity" : "n",
                "containment" : "no"
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                                           
        },
        {
            "id": "4",
            "type": "default",
            "origin": "1",
            "target": "5",
            "abstract": {
                "name" : "sons",
                "multiplicity" : "n",
                "containment" : "no"
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                               
        },
        {
            "id": "5",
            "type": "default",
            "origin": "1",
            "target": "6",
            "abstract": {
                "name" : "daughters",
                "multiplicity" : "n",
                "containment" : "no"
            },
            "editorOptions": {
                "movable" : "True",
                "editable" : "True"
            }                               
        }       
    ]
}

// We keep the last version of the IRF (to be able to make comparisons)
var oldirf = JSON.stringify(irf, undefined, 2);

// This is the library of SVG templates
// At some point, it should be retrieved from the server
// For now the map includes a single entry for the SVG rectangle which includes a SVG text inside. 
// Note that the text elemetn has an id with value "name" which will be a placeholder for the text to add
templates =  {
    "Family" : 
        '<g class="rotatable"><g class="scalable">' +
        '<rect y="0" x="0" height="10" width="20" style="color:#000000;opacity:1;vector-effect:none;fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.25;stroke-opacity:1;"/>' +
        '</g>'+
        '<text id="name" y="20" x="5" style="font-family:sans-serif;fill:#000000;fill-opacity:1;"></text>' + 
        '<text id="lastName" y="40" x="25" style="font-family:sans-serif;fill:#000000;fill-opacity:1;"></text>' + 
        '</g>'
        ,
    "Member" : 
        '<g class="rotatable"><g class="scalable">' +
        '<rect y="0" x="0" height="10" width="20" style="color:#000000;opacity:1;vector-effect:none;fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.25;stroke-opacity:1;"/>' +
        '</g>'+
        '<text id="name" y="20" x="5" style="font-family:sans-serif;fill:#000000;fill-opacity:1;"></text>' + 
        '<text id="firstName" y="40" x="25" style="font-family:sans-serif;fill:#000000;fill-opacity:1;"></text>' + 
        '</g>'
}

// We will store here two maps to keep track of the JoinsJS elements given a node/edge id 
// (useful when adding edges)
// and viceversa (useful wehn editing source/target edges)
var jointJSElements = {}
var IRFElements = {}

// We keep track of the last created node Id
var lastCreatedNodeId = undefined 

/**
 * Creates a node
 * @param {string} nodeType The node type to create (it HAS TO exist in templates map)
 * @param nodeInfo The info of the node
 */
function createNode(nodeType, nodeInfo) {

    // We first recover the SVG template given the nodeType
    template = templates[nodeType]

    // We initialize the attributes of the node (coming from the abstract section)
    // This attributes will be passed to JoinJS in the attrs param of the Generic constructor (see below)
    var nodeAttrs = {}
    nodeAttrs["image"] = { width: nodeInfo.concrete.width, height: nodeInfo.concrete.height } // this attr is always added
    for(var key in nodeInfo.abstract) { // We itetare over the abstract elements
        var digestedKey = '#' + key;
        nodeAttrs[digestedKey] = { text : nodeInfo.abstract[key] };
    }

    // We create the node in JointJS
    nodeGraph = new joint.shapes.basic.Generic({
        markup: template, 
        attrs : nodeAttrs, // we add here the previously created attributes
        size: { width: nodeInfo.concrete.width, height: nodeInfo.concrete.height },
        position: { x: nodeInfo.diagram.x, y: nodeInfo.diagram.y },
        inPorts: ['in'],
        outPorts: ['out']
    });

    // We add listeners
    // Updating positions in the IRF if the node is moved
    nodeGraph.on('change:position', function(element) {
        //console.log("Node: ", nodeGraph.id, " moved to x:", element.get('position').x, " y:", element.get('position').y)
        nodeInfo.diagram.x = element.get('position').x
        nodeInfo.diagram.y = element.get('position').y
    })

    // Node is added to the canvas 
    graph.addCell(nodeGraph);

    // We store the JoinsJS element in the maps to keep track
    jointJSElements[nodeInfo.id] = nodeGraph;
    IRFElements[nodeGraph.id] = nodeInfo;

    // We keep track of the last created node
    lastCreatedNodeId = nodeInfo.id;
}

/**
 * Creates an edge
 * @param {string} edgeType The edge type to create 
 * @param edgeInfo The info of the edge
 */
function createEdge(edgeType, edgeInfo) {
    // For now we only support default edges (normal lines in JointJS)
    if(edgeType == "default") {
        // We retrieve the JointJS node given the IRF node id for source and target
        source = jointJSElements[edgeInfo.origin];
        target = jointJSElements[edgeInfo.target];
        edgeGraph = new joint.shapes.devs.Link({
            source: { id: source.id, port: 'out' },
            target: { id: target.id, port: 'in' }
        });

        // We listen if the target changes
        edgeGraph.on('change:target', function(link) {
            // We check if it actually changes (JointJS also triggers this event even if the edges moves)
            if(link.get("target").id != undefined) {  
                irfNodeId = IRFElements[link.get("target").id].id // We recover the IRF node id fro mour map
                edgeInfo.target = irfNodeId;
            }
        });

        // We listen if the source changes
        edgeGraph.on('change:source', function(element) {
            // We check if it actually changes (JointJS also triggers this event even if the edges moves)
            if(link.get("source").id != undefined) { 
                irfNodeId = IRFElements[link.get("source").id].id  // We recover the IRF node id fro mour map
                edgeInfo.origin = irfNodeId;
            }
        });

        // Edge is added to the canvas
        graph.addCell(edgeGraph)
    }

}

/**
 * This auxiliary function generates boxes
 * with random names and coordinates
 */
function generateBox() {
    var randomId = Math.floor(Math.random() * 101);
    var randomX = Math.floor(Math.random() * 200);
    var randomY= Math.floor(Math.random() * 200);
    nodeGenerated =  
        { "node" : {
            "id" : "box" + randomId,
            "type" : "box",
            "abstract" : { "name" : "B" + randomId, "firstName" : "Son" + randomId },
            "concrete" : { "width" : 100, "height" : 50, },
            "diagram"  : {  "x" : randomX, "y" : randomY, "z" : 0 },
            "editorOptions" : { "movable" : true, "editable" : true } 
            }
        };
    return nodeGenerated;
}

/**
 * Adds a node to the canvas 
 */
function addNode() {
    // veryLastNodeId = lastCreatedNodeId;
    veryLastNodeId = "1"; //Family node
    nodeGenerated = generateBox();
    // createNode(nodeGenerated.node.type, nodeGenerated.node);

    createNode("Member", nodeGenerated.node);

    if(veryLastNodeId != undefined) {
        edgeGenerated = 
            { "edge" : {
                "id" : nodeGenerated.node.id + "Edge",
                "type" : "default",
                "origin" : veryLastNodeId,
                "target" : nodeGenerated.node.id,
                "editorOptions" : { "movable" : false,"editable" : false} 
                }
            };
        createEdge(edgeGenerated.edge.type, edgeGenerated.edge);
    }
}

/**
 * Initializes the canvas with an IRF
 * @param irf The IRF definition
 */
function interpretIRF(irf) {
    var i;

   // First we draw the nodes
    for (i = 0; i < irf.nodes.length; i++) {
        // log(i);
        element = irf.nodes[i];
        createNode(element.type, element);
    }

    // Then we draw the edges
    // (so we are sure we can resolve sources/targets)
    for (i = 0; i < irf.edges.length; i++) {
        element = irf.edges[i];
        createEdge(element.type, element);
    }

    // // First we draw the nodes
    // for (i = 0; i < irf.elements.length; i++) {
    //     element = irf.elements[i];
    //     if(element.node != undefined) {
    //         createNode(element.node.type, element.node);
    //     }
    // }

    // // Then we draw the edges
    // // (so we are sure we can resolve sources/targets)
    // for (i = 0; i < irf.elements.length; i++) {
    //     element = irf.elements[i];
    //     if(element.edge != undefined) {
    //         createEdge(element.edge.type, element.edge);
    //     }
    // }
}

// --------------------------------------------------------
// DIAGRAM 
// --------------------------------------------------------

// Basic initialization things
var graph = new joint.dia.Graph();
var paper = new joint.dia.Paper({
    el: $('#paper'),
    width: 1140,
    height: 600,
    gridSize: 1,
    model: graph
});

var uml = joint.shapes.uml;

$('#initializeButton').attr('disabled', 'disabled');
$('#shutdownButton').attr('disabled', 'disabled');
$('#exitButton').attr('disabled', 'disabled');
$('#didOpenButton').attr('disabled', 'disabled');
$('#willSaveButton').attr('disabled', 'disabled');
$('#didSaveButton').attr('disabled', 'disabled');
$('#validateButton').attr('disabled', 'disabled');
$('#validateWithErrorButton').attr('disabled', 'disabled');
$('#svgButton').attr('disabled', 'disabled');
$('#didCloseButton').attr('disabled', 'disabled');
$('#didChangeButton').attr('disabled', 'disabled');


// --------------------------------------------------------
// UTILS 
// --------------------------------------------------------

// This function allows us to log things in the bottom text box.
function log(_msg){
    currentText = $('#notification').val();
    newText = currentText + "\n" + _msg;
    $('#notification').val(newText);
    $('#notification').scrollTop($('#notification')[0].scrollHeight);
}

function printIRF() {
    var irfString = JSON.stringify(irf, undefined, 2);
    log(irfString);
    console.log(irfString);
}

// --------------------------------------------------------
// LSP  
// --------------------------------------------------------

// We use JRPC
var jrpc = new simple_jsonrpc();
var xhr = new XMLHttpRequest();
// var socket = new WebSocket("ws://localhost:8080");

// socket.onmessage = function(event) {
//             jrpc.messageHandler(event.data);
//         };

// jrpc.toStream = function(_msg){
//     log("Content-Length: "+_msg.length+"\n"+_msg);
//             socket.send("Content-Length: "+_msg.length+"\n"+_msg);
//         };

// socket.onerror = function(error) {
//             log("Error: " + error.message);
//             console.error("Error: " + error.message);
//         };

// socket.onclose = function(event) {
//             if (event.wasClean) {
//                 console.info('Connection close was clean');
//             } else {
//                 console.error('Connection suddenly close');
//             }
//             console.info('close code : ' + event.code + ' reason: ' + event.reason);
//         };

//         socket.onopen = function(){
// };

// Basic configuration
jrpc.toStream = function(_msg){
    
    xhr.onreadystatechange = function() {
        // log("======== Ready State: "+xhr.readyState);
        //     log(this.responseText);
        if (this.readyState != 4) return;
        if (this.responseText == undefined || this.responseText == "") return;
        try {            
            JSON.parse(this.responseText);
            jrpc.messageHandler(this.responseText);
        } catch (e){
            console.error(e);
        }
    };
    xhr.open("POST", 'http://localhost:8080', true); // Set here the host and port
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(_msg);
};

// jrpc.on('window/showMessage', 'pass', function(result) {
jrpc.on('window/showMessage', function(result) {

    log(result.message);
    console.log("Show message" + result);

});

//Load IRF form its definition
function load() {

    interpretIRF(irf);
    $('#initializeButton').removeAttr('disabled');

}


//Instance of server capabilities we are to receive from the server
var serverCapabilities = {}

function initialize() {
     
    iniParams = {
        "processId" : 0,
        "rootPath" : null,
        "rootUri" : "C:\\Users\\jcanovasi\\git\\lsp\\HelloWorldLanguageServer",
        "initializationOptions" : null,
        "capabilities" :  {
            "workspace" : {
                "applyEdit" : true,
                "workspaceEdit" : {
                    "documentChanges" : true
                },
                "didChangeConfiguration" : {
                    "dynamicRegistration" : true
                },
                "didChangeWatchedFiles" : {
                    "dynamicRegistration" : false
                },
                "symbol" : {
                    "dynamicRegistration" : true
                },
                "executeCommand" : {
                    "dynamicRegistration" : true
                },
                "workspaceFolders" : false
            },
            "textDocument" : {
                "synchronization" : {
                    "willSave" : true,
                    "willSaveWaitUntil" : true,
                    "didSave" : true,
                    "dynamicRegistration" : null
                },
                "completion" : {
                    "completionItem" : null,
                    "dynamicRegistration" : false
                },
                "hover" : {
                    "dynamicRegistration" : false
                },
                "signatureHelp" : {
                    "dynamicRegistration" : false
                },
                "references" : {
                    "dynamicRegistration" : true
                },
                "documentHighlight" : {
                    "dynamicRegistration" : true
                },
                "documentSymbol" : {
                    "dynamicRegistration" : true
                },             
                "formatting" : {
                    "dynamicRegistration" : false
                },
                "rangeFormatting" : {
                    "dynamicRegistration" : false
                },
                "onTypeFormatting" : {
                    "dynamicRegistration" : false
                },
                "definition" : {
                    "dynamicRegistration" : true
                },
                "codeAction" : {
                    "dynamicRegistration" : true
                },
                "codeLens" : {
                    "dynamicRegistration" : true
                },
                "documentLink" : {
                    "dynamicRegistration" : true
                },
                "rename" : {
                    "dynamicRegistration" : true
                }
            },    
            "experimental" : null
        },       
        "clientName" : "Adrian - Language server client",
        "trace" : "off"
    };

    jrpc.call('initialize', iniParams).then(function (result) {
        serverCapabilities = result;
        log("\nInitialize message sent");
        log("Response: "+JSON.stringify(result));
        console.log(JSON.stringify(result));
    });

    $('#initializeButton').attr('disabled', 'disabled');
    $('#shutdownButton').removeAttr('disabled');
    $('#didOpenButton').removeAttr('disabled');
    $('#willSaveButton').removeAttr('disabled');
    $('#didSaveButton').removeAttr('disabled');
    $('#validateButton').removeAttr('disabled');
    $('#validateWithErrorButton').removeAttr('disabled');
    $('#svgButton').removeAttr('disabled');
    $('#didCloseButton').removeAttr('disabled');
    $('#didChangeButton').removeAttr('disabled');
    
    
}

//Save model locally and export it to IRF syntax
function save() {

    log("\nSaving model..."); 
    
    var diffs = JsDiff.diffLines(oldirf, JSON.stringify(irf, undefined, 2));

    var linesIndex = 0; // This variable counts the lines in the irf
    var textDocumentContentChangeEvents = []; // The set of changes

    diffs.forEach(function(diff) {

        var lines = diff.count;

        if(diff.added == undefined && diff.removed == undefined) {

            // This diff does not signal any change, so we only add the lines
            linesIndex = linesIndex + lines; 
        
        } else if (diff.added != undefined && diff.removed == undefined) {
           
            // This diff signals that text was added
            // We build the LSP required element for didChange
            var textDocumentContentChangeEvent = {
                range : {
                    start : { line: linesIndex+1, character: 0},
                    end   : { line: linesIndex+1, character: 0}
                },
                text : diff.value
            }
            
            textDocumentContentChangeEvents.push(textDocumentContentChangeEvent);
        
        } else if (diff.added == undefined && diff.removed != undefined) {

            // This diff signals that text was removed
            // We need to calculate the ending col
            // We retrieve the changed text and count the number of characters of the last line
            var actualLines = diff.value.split("\n");         // We split the lines of the changes
            var lastLine = actualLines[actualLines.length-2]; // We take the one previous to the last one. TODO: Control the array size!
            var lastChar = lastLine.length;                   // We count the characters

            // We build the LSP required element for didChange
            var textDocumentContentChangeEvent = {
                range : {
                    start : { line: linesIndex+1, character: 0},
                    end   : { line: linesIndex+lines, character: lastChar+1}
                },
                text : ""
            }

            textDocumentContentChangeEvents.push(textDocumentContentChangeEvent);
        
        }
    
    });

    // We send the changes using a didchange
    // We build the params
    didChangeTextDocumentParams = {

        textDocument : JSON.stringify(irf),
        contentChanges : textDocumentContentChangeEvents

    };

    log("Changes applied locally: " + JSON.stringify(didChangeTextDocumentParams));

    // We reset the last snapshot of the IRF
    oldirf = JSON.stringify(irf, undefined, 2);

}

//Notify server we've opened a document on the client side
function didOpen() {
    
    var textDocumentItem = new Object(); 
    textDocumentItem.uri        = "jsonFile.txt";
    textDocumentItem.languageId = "json";
    textDocumentItem.version    = 1;
    textDocumentItem.text       = JSON.stringify(irf);

    var didOpenTextDocumentParams = new Object();
    didOpenTextDocumentParams.textDocument = textDocumentItem;

    jrpc.call('textDocument/didOpen', didOpenTextDocumentParams).then(function (result) {
        log("\ndidOpen message sent");
        log("Response: "+ JSON.stringify(result));
    });

}

//Notify server model will be saved in the client side
function willSave() {

    var willSaveTextDocumentParams = new Object();
    var didSaveTextDocumentParams  = new Object();
    var textDocumentIdentifier     = new Object();
    
    //willSave notification
    textDocumentIdentifier.uri              = "jsonFile.txt";
    willSaveTextDocumentParams.textDocument = textDocumentIdentifier;
    willSaveTextDocumentParams.reason       = "TextDocumentSaveReason.Manual";

    jrpc.call('textDocument/willSave', willSaveTextDocumentParams).then(function (result) {
        log("\nwillSave message sent");
        log("Response: "+JSON.stringify(result));
        console.log(JSON.stringify(result));
    });

}

//Notify changes on the file
function didChange(){

    var diffs = JsDiff.diffLines(oldirf, JSON.stringify(irf, undefined, 2));

    var linesIndex                      = 0; // This variable counts the lines in the irf
    var lines                           = 0;
    var positionStart                   = new Object();
    var positionEnd                     = new Object();
    var textDocumentContentChangeEvents = new Object();
    var textDocumentRange               = new Object();

    diffs.forEach(function(diff) {

        lines = diff.count;

        if(diff.added == undefined && diff.removed == undefined) {

            // This diff does not signal any change, so we only add the lines
            linesIndex = linesIndex + lines; 
        
        } else if (diff.added != undefined && diff.removed == undefined) {
           
            positionStart.line      = linesIndex + 1;
            positionStart.character = 0;
            positionEnd.line        = linesIndex + 1;
            positionEnd.character   = 0;

            textDocumentRange.start = positionStart;
            textDocumentRange.end   = positionEnd;

            textDocumentContentChangeEvents.range       = textDocumentRange;
            textDocumentContentChangeEvents.rangeLength = 10;
            textDocumentContentChangeEvents.text        = JSON.stringify(irf);
        
        } else if (diff.added == undefined && diff.removed != undefined) {

            // This diff signals that text was removed
            // We need to calculate the ending col
            // We retrieve the changed text and count the number of characters of the last line
            var actualLines = diff.value.split("\n");         // We split the lines of the changes
            var lastLine    = actualLines[actualLines.length-2]; // We take the one previous to the last one. TODO: Control the array size!
            var lastChar    = lastLine.length;                   // We count the characters


            positionStart.line      = linesIndex + 1;
            positionStart.character = 0;
            positionEnd.line        = linesIndex + lines;
            positionEnd.character   = lastChar + 1;
            
            textDocumentRange.start = positionStart;
            textDocumentRange.end   = positionEnd;

            textDocumentContentChangeEvents.range       = textDocumentRange;
            textDocumentContentChangeEvents.rangeLength = 10;
            textDocumentContentChangeEvents.text        = JSON.stringify(irf);
        
        }
    
    });


    var versionedTextDocumentIdentifier     = new Object();
    versionedTextDocumentIdentifier.version = 1;
    versionedTextDocumentIdentifier.uri     = "jsonFile.txt";

    var contentChangesList = [textDocumentContentChangeEvents];

    var didChangeTextDocumentParams            = new Object();
    didChangeTextDocumentParams.textDocument   = versionedTextDocumentIdentifier;
    didChangeTextDocumentParams.contentChanges = contentChangesList;

    jrpc.call('textDocument/didChange', didChangeTextDocumentParams).then(function (result) {
        log("\ndidClhange message sent");
        log("Response: "+ JSON.stringify(result));
    });

}


//Notify the server we closed a document on the client side
function didClose(){

    var versionedTextDocumentIdentifier     = new Object();
    versionedTextDocumentIdentifier.version = 1;
    versionedTextDocumentIdentifier.uri     = "jsonFile.txt";

    var didCloseTextDocumentParams = new Object();
    didCloseTextDocumentParams.textDocument = versionedTextDocumentIdentifier;

    jrpc.call('textDocument/didClose', didCloseTextDocumentParams).then(function (result) {
        log("\ndidClose message sent");
        log("Response: "+ JSON.stringify(result));
    });

}

//Notify server model will be saved in the server side
function didSave() {

    var didSaveTextDocumentParams  = new Object();
    var textDocumentIdentifier     = new Object();
    
    //didSave notification
    textDocumentIdentifier.uri             = "jsonFile.txt";
    didSaveTextDocumentParams.textDocument = textDocumentIdentifier;
    didSaveTextDocumentParams.text         = JSON.stringify(irf);;

    jrpc.call('textDocument/didSave', didSaveTextDocumentParams).then(function (result) {
        log("\ndidSave message sent");
        log("Response: "+JSON.stringify(result));
        console.log(JSON.stringify(result));
    });

}

//Validate model on the server side
function validate() {

    var executeCommandParams     = new Object();
    executeCommandParams.command = "validate";

    var argumentsList = [irf];

    executeCommandParams.arguments = argumentsList;

    jrpc.call('workspace/executeCommand', executeCommandParams).then(function (result) {
        log("\nValidate message sent");
        log("Response: "+ JSON.stringify(result));
        console.log(JSON.stringify(result));
    });
  
}

//Validate model on the server side with an adhoc error, father sssociation missing
function validateError() {

    var irfWithError =
    {
       "rootElement": "Family",
       "namedElementPackage": "family.model.family.impl.NamedElementImpl",
       "implPackage": "family.model.family.impl.",
       "nodes": [
           {
               "id": "1",
               "type": "Family",
               "abstract" : {
                   "name" : "F1",
                   "lastName" : "Ramirez"
               },
               "concrete" : { "width" : 100, "height" : 50 },
               "diagram": {
                   "x" : 50, 
                   "y" : 40, 
                   "z" : 0
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }           
           },
           {
               "id": "2",
               "type": "Member",
               "abstract": {
                   "name" : "M1",
                   "firstName" : "Julio",
                   "age" : 45,
                   "gender" : "male"
               },
               "concrete" : { "width" : 100, "height" : 50 },
               "diagram": {
                   "x" : 160, 
                   "y" : 40, 
                   "z" : 0
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }               
           },
           {
               "id": "3",
               "type": "Member",
               "abstract": {
                   "name" : "M2",
                   "firstName" : "Margarita",
                   "age" : 40,
                   "gender" : "female"
               },
               "concrete" : { "width" : 100, "height" : 50 },
               "diagram": {
                   "x" : 280, 
                   "y" : 40, 
                   "z" : 0
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }                   
           },
           {
               "id": "4",
               "type": "Member",
               "abstract": {
                   "name" : "M3",
                   "firstName" : "Hugo",
                   "age" : 15,
                   "gender" : "male"
               },
               "concrete" : { "width" : 100, "height" : 50 },
               "diagram": {
                   "x" : 50, 
                   "y" : 110, 
                   "z" : 0
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }                       
           },
           {
               "id": "5",
               "type": "Member",
               "abstract": {
                   "name" : "M4",
                   "firstName" : "Adrian",
                   "age" : 8,
                   "gender" : "male"
               },
               "concrete" : { "width" : 100, "height" : 50 },
               "diagram": {
                   "x" : 50, 
                   "y" : 220, 
                   "z" : 0
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }                       
           },
           {
               "id": "6",
               "type": "Member",
               "abstract": {
                   "name" : "M5",
                   "firstName" : "Sofia",
                   "age" : 10,
                   "gender" : "female"
               },  
               "concrete" : { "width" : 100, "height" : 50 },        
               "diagram": {
                   "x" : 210, 
                   "y" : 220, 
                   "z" : 0
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }               
           }       
       ],
       "edges": [
           {
               "id": "2",
               "type": "default",
               "origin": "1",
               "target": "3",
               "abstract": {
                   "name" : "mother",
                   "multiplicity" : "1",
                   "containment" : "yes"
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }                                           
           },
           {
               "id": "3",
               "type": "default",
               "origin": "1",
               "target": "4",
               "abstract": {
                   "name" : "sons",
                   "multiplicity" : "n",
                   "containment" : "no"
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }                                           
           },
           {
               "id": "4",
               "type": "default",
               "origin": "1",
               "target": "5",
               "abstract": {
                   "name" : "sons",
                   "multiplicity" : "n",
                   "containment" : "no"
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }                               
           },
           {
               "id": "5",
               "type": "default",
               "origin": "1",
               "target": "6",
               "abstract": {
                   "name" : "daughters",
                   "multiplicity" : "n",
                   "containment" : "no"
               },
               "editorOptions": {
                   "movable" : "True",
                   "editable" : "True"
               }                               
           }       
       ]    
    }
   
    var executeCommandParams     = new Object();
    executeCommandParams.command = "validate";

    var argumentsList = [irfWithError];

    executeCommandParams.arguments = argumentsList;

    jrpc.call('workspace/executeCommand', executeCommandParams).then(function (result) {
        log("\nValidate message sent");
        log("Response: "+ JSON.stringify(result));
        console.log(JSON.stringify(result));
    });
  
}

//Get SVG file from the server
function getSVGFile() {

    var executeCommandParams     = new Object();
    executeCommandParams.command = "svg";

    var argumentsList = ["myLanguage"];

    executeCommandParams.arguments = argumentsList;

    jrpc.call('workspace/executeCommand', executeCommandParams).then(function (result) {
        log("\nSVG message sent");
        log("Response: "+ JSON.stringify(result));
        console.log(JSON.stringify(result));
    });

}

//Shutdown wiht the language server
function shutdown() {

    jrpc.call('shutdown', null).then(function (result) {
        log("\nShutdown message sent");
        log("Response: "+ JSON.stringify(result));
        console.log(JSON.stringify(result));
    });

    $('#shutdownButton').attr('disabled', 'disabled');
    $('#exitButton').removeAttr('disabled');
    $('#initializeButton').removeAttr('disabled');

    $('#didOpenButton').attr('disabled', 'disabled');
    $('#willSaveButton').attr('disabled', 'disabled');
    $('#didSaveButton').attr('disabled', 'disabled');
    $('#validateButton').attr('disabled', 'disabled');
    $('#validateWithErrorButton').attr('disabled', 'disabled');
    $('#svgButton').attr('disabled', 'disabled');
    $('#didCloseButton').attr('disabled', 'disabled');
    $('#didChangeButton').attr('disabled', 'disabled');   
    
}

//Exit the language server
function exit() {

    jrpc.notification('exit', null);
    log("\nExit message sent");

    $('#shutdownButton').attr('disabled', 'disabled');
    $('#exitButton').attr('disabled', 'disabled');

}



