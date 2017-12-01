# Jquery-logger
Simple Logger extend Jquery

### Feature
Package and extend the Console.

eg:
```javascript
var first = "jquery";
var last = "logger";
$.logError("using {}-{}",first,last);
```
call in fact 
```javascript
var first = "jquery";
var last = "logger";
console.error("using " + first + "-" + logger);
```
will output in the console

`using jquery-logger`

### Usage

```javascript
<script type="text/javascript" src="jquery-logger.js"></script>
<script type="text/javascript">
  $(function () {
    $.logClear();
    $.logAssert(1 < 0, "error message");
    $.logCount("hello");
    $.logDebug();
    $.logDebug("hello: {},{}", "jquery", "logger");
    $.logError("hello: {},{}", "jquery", "logger");
    $.logInfo("hello: {},{}", "jquery", "logger");
    $.log("hello: {},{}", "jquery", "logger");
    $.logTable([new User("Tom", "Andy"), new User("Jerry", "Andy")]);
    $.logTrace("hello: {},{}", "jquery", "logger");
    $.logWarn("hello: {},{}", "jquery", "logger");
    $.logGroup("hello: {},{}", "jquery", "logger");
    $.log("jquery");
    $.log("logger");
    $.logGroupEnd();
    $.logGroupCollapsed("hello: {},{}", "jquery", "logger");
    $.log("jquery");
    $.log("logger");
    $.logGroupEnd();
    function User(firstName, lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
    }
  })
</script>
```