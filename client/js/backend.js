
// Our IRF definition
var irf = {
    "version" : "v1",
    "language" : "UMLClass",
    "elements" : [
        { "node" : {
            "id" : "b1",
            "type" : "box",
            "abstract" : { "name" : "A" },
            "concrete" : { "width" : 100, "height" : 50, },
            "diagram"  : {  "x" : 50, "y" : 40, "z" : 0 },
            "editorOptions" : { "movable" : true, "editable" : true } 
            }
        },
        { "node" : {
            "id" : "b2",
            "type" : "box",
            "abstract" : { "name" : "B" },
            "concrete" : { "width" : 100, "height" : 50, },
            "diagram"  : {  "x" : 200, "y" : 180, "z" : 0 },
            "editorOptions" : { "movable" : true, "editable" : true } 
            }
        },
        { "edge" : {
            "id" : "b1-b2",
            "type" : "default",
            "origin" : "b1",
            "target" : "b2",
            "editorOptions" : { "movable" : false,"editable" : false
            } 
        }}
    ]
}

// We keep the last version of the IRF (to be able to make comparisons)
var oldirf = JSON.stringify(irf, undefined, 2);

// This is the library of SVG templates
// At some point, it should be retrieved from the server
// For now the map includes a single entry for the SVG rectangle which includes a SVG text inside. 
// Note that the text elemetn has an id with value "name" which will be a placeholder for the text to add
templates =  {
    "box" : 
        '<g class="rotatable"><g class="scalable">' +
        '<rect y="0" x="0" height="10" width="20" style="color:#000000;opacity:1;vector-effect:none;fill:#ffffff;fill-opacity:1;stroke:#000000;stroke-width:0.25;stroke-opacity:1;"/>' +
        '</g>'+
        '<text id="name" y="30" x="45" style="font-family:sans-serif;fill:#000000;fill-opacity:1;"></text>' + 
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
            "abstract" : { "name" : "B" + randomId },
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
    veryLastNodeId = lastCreatedNodeId;
    nodeGenerated = generateBox();
    createNode(nodeGenerated.node.type, nodeGenerated.node);

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
    for (i = 0; i < irf.elements.length; i++) {
        element = irf.elements[i];
        if(element.node != undefined) {
            createNode(element.node.type, element.node);
        }
    }

    // Then we draw the edges
    // (so we are sure we can resolve sources/targets)
    for (i = 0; i < irf.elements.length; i++) {
        element = irf.elements[i];
        if(element.edge != undefined) {
            createEdge(element.edge.type, element.edge);
        }
    }
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

// --------------------------------------------------------
// UTILS 
// --------------------------------------------------------

// This function allows us to leg things in the bottom text box.
function log(msg){
    currentText = $('#notification').val();
    newText = currentText + "\n" + msg;
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

// Basic configuration
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
    xhr.open("POST", 'http://localhost:8080', true); // Set here the host and port
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(_msg);
};

// buttons
function load() {
    interpretIRF(irf);
    $('#initializeButton').removeAttr('disabled');
}

function initialize() {
    iniParams = {
        "processId" : 0,
        "rootUri" : "C:\\Users\\jcanovasi\\git\\lsp\\HelloWorldLanguageServer",
        "capabilities" :  {
            "textDocument" : null,
            "workspace" : null,
            "experimental" : null
        }
    };
    jrpc.call('initialize', iniParams).then(function (result) {
        log("Initialize message sent");
        console.log(result);
    });
    $('#initializeButton').attr('disabled', 'disabled');
    $('#shutdownButton').removeAttr('disabled');
}

function save() {
    //console.log(oldirf);
    //console.log(JSON.stringify(irf, undefined, 2));

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
        textDocument : "what?",
        contentChanges : textDocumentContentChangeEvents
    };
    console.log(didChangeTextDocumentParams);

    // We call by using LSP
    jrpc.call('didChange', didChangeTextDocumentParams).then(function (result) {
        log("dodChange sent");
        console.log(result);
    });

    // We reset the last snapshot of the IRF
    oldirf = JSON.stringify(irf, undefined, 2);
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

