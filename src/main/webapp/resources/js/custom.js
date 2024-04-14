window.addEventListener("pageshow", function (event) {
    const historyTraversal = event.persisted ||
        (typeof window.performance != "undefined" &&
            window.performance.navigation.type === 2);
    if (historyTraversal) {
        // Handle page restore.
        window.location.reload();
    }
});
function copyValue(className) {
    var fields = document.getElementsByClassName(className);
    if(fields.length>0) {
        var range = document.createRange();
        range.selectNodeContents(fields[0]);

        var selection = window.getSelection();
        selection.removeAllRanges();
        selection.addRange(range);
        document.execCommand("copy");
    }

}