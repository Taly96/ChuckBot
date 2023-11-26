@REM ----------------------------------------------------------------------------
@REM Copyright 2001-2004 The Apache Software Foundation.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM ----------------------------------------------------------------------------
@REM

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\telegram\telegrambots\6.0.1\telegrambots-6.0.1.jar;"%REPO%"\org\telegram\telegrambots-meta\6.0.1\telegrambots-meta-6.0.1.jar;"%REPO%"\com\google\guava\guava\30.0-jre\guava-30.0-jre.jar;"%REPO%"\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar;"%REPO%"\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;"%REPO%"\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar;"%REPO%"\org\checkerframework\checker-qual\3.5.0\checker-qual-3.5.0.jar;"%REPO%"\com\google\errorprone\error_prone_annotations\2.3.4\error_prone_annotations-2.3.4.jar;"%REPO%"\com\google\j2objc\j2objc-annotations\1.3\j2objc-annotations-1.3.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-annotations\2.13.2\jackson-annotations-2.13.2.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-json-provider\2.13.2\jackson-jaxrs-json-provider-2.13.2.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-base\2.13.2\jackson-jaxrs-base-2.13.2.jar;"%REPO%"\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.13.2\jackson-module-jaxb-annotations-2.13.2.jar;"%REPO%"\jakarta\xml\bind\jakarta.xml.bind-api\2.3.3\jakarta.xml.bind-api-2.3.3.jar;"%REPO%"\org\glassfish\jersey\inject\jersey-hk2\2.32\jersey-hk2-2.32.jar;"%REPO%"\org\glassfish\jersey\core\jersey-common\2.32\jersey-common-2.32.jar;"%REPO%"\org\glassfish\hk2\osgi-resource-locator\1.0.3\osgi-resource-locator-1.0.3.jar;"%REPO%"\com\sun\activation\jakarta.activation\1.2.2\jakarta.activation-1.2.2.jar;"%REPO%"\org\glassfish\hk2\hk2-locator\2.6.1\hk2-locator-2.6.1.jar;"%REPO%"\org\glassfish\hk2\external\aopalliance-repackaged\2.6.1\aopalliance-repackaged-2.6.1.jar;"%REPO%"\org\glassfish\hk2\hk2-api\2.6.1\hk2-api-2.6.1.jar;"%REPO%"\org\glassfish\hk2\hk2-utils\2.6.1\hk2-utils-2.6.1.jar;"%REPO%"\org\javassist\javassist\3.25.0-GA\javassist-3.25.0-GA.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-json-jackson\2.32\jersey-media-json-jackson-2.32.jar;"%REPO%"\org\glassfish\jersey\ext\jersey-entity-filtering\2.32\jersey-entity-filtering-2.32.jar;"%REPO%"\org\glassfish\jersey\containers\jersey-container-grizzly2-http\2.32\jersey-container-grizzly2-http-2.32.jar;"%REPO%"\org\glassfish\hk2\external\jakarta.inject\2.6.1\jakarta.inject-2.6.1.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http-server\2.4.4\grizzly-http-server-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http\2.4.4\grizzly-http-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-framework\2.4.4\grizzly-framework-2.4.4.jar;"%REPO%"\jakarta\ws\rs\jakarta.ws.rs-api\2.1.6\jakarta.ws.rs-api-2.1.6.jar;"%REPO%"\org\glassfish\jersey\core\jersey-server\2.32\jersey-server-2.32.jar;"%REPO%"\org\glassfish\jersey\core\jersey-client\2.32\jersey-client-2.32.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-jaxb\2.32\jersey-media-jaxb-2.32.jar;"%REPO%"\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;"%REPO%"\jakarta\validation\jakarta.validation-api\2.0.2\jakarta.validation-api-2.0.2.jar;"%REPO%"\org\json\json\20180813\json-20180813.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.5.13\httpclient-4.5.13.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.4.13\httpcore-4.4.13.jar;"%REPO%"\commons-codec\commons-codec\1.11\commons-codec-1.11.jar;"%REPO%"\org\apache\httpcomponents\httpmime\4.5.13\httpmime-4.5.13.jar;"%REPO%"\commons-io\commons-io\2.11.0\commons-io-2.11.0.jar;"%REPO%"\org\slf4j\slf4j-api\1.7.32\slf4j-api-1.7.32.jar;"%REPO%"\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar;"%REPO%"\com\squareup\okhttp3\okhttp\4.10.0\okhttp-4.10.0.jar;"%REPO%"\com\squareup\okio\okio-jvm\3.0.0\okio-jvm-3.0.0.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib-jdk8\1.5.31\kotlin-stdlib-jdk8-1.5.31.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib-jdk7\1.5.31\kotlin-stdlib-jdk7-1.5.31.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib-common\1.5.31\kotlin-stdlib-common-1.5.31.jar;"%REPO%"\org\jetbrains\kotlin\kotlin-stdlib\1.6.20\kotlin-stdlib-1.6.20.jar;"%REPO%"\org\jetbrains\annotations\13.0\annotations-13.0.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-databind\2.13.0\jackson-databind-2.13.0.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-core\2.13.0\jackson-core-2.13.0.jar;"%REPO%"\net\sourceforge\htmlunit\htmlunit\2.52.0\htmlunit-2.52.0.jar;"%REPO%"\xalan\xalan\2.7.2\xalan-2.7.2.jar;"%REPO%"\xalan\serializer\2.7.2\serializer-2.7.2.jar;"%REPO%"\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;"%REPO%"\org\apache\commons\commons-text\1.9\commons-text-1.9.jar;"%REPO%"\net\sourceforge\htmlunit\htmlunit-core-js\2.52.0\htmlunit-core-js-2.52.0.jar;"%REPO%"\net\sourceforge\htmlunit\neko-htmlunit\2.52.0\neko-htmlunit-2.52.0.jar;"%REPO%"\xerces\xercesImpl\2.12.0\xercesImpl-2.12.0.jar;"%REPO%"\xml-apis\xml-apis\1.4.01\xml-apis-1.4.01.jar;"%REPO%"\net\sourceforge\htmlunit\htmlunit-cssparser\1.9.0\htmlunit-cssparser-1.9.0.jar;"%REPO%"\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;"%REPO%"\commons-net\commons-net\3.8.0\commons-net-3.8.0.jar;"%REPO%"\org\brotli\dec\0.1.2\dec-0.1.2.jar;"%REPO%"\com\shapesecurity\salvation2\3.0.0\salvation2-3.0.0.jar;"%REPO%"\org\eclipse\jetty\websocket\websocket-client\9.4.43.v20210629\websocket-client-9.4.43.v20210629.jar;"%REPO%"\org\eclipse\jetty\jetty-client\9.4.43.v20210629\jetty-client-9.4.43.v20210629.jar;"%REPO%"\org\eclipse\jetty\jetty-http\9.4.43.v20210629\jetty-http-9.4.43.v20210629.jar;"%REPO%"\org\eclipse\jetty\jetty-util\9.4.43.v20210629\jetty-util-9.4.43.v20210629.jar;"%REPO%"\org\eclipse\jetty\jetty-io\9.4.43.v20210629\jetty-io-9.4.43.v20210629.jar;"%REPO%"\org\eclipse\jetty\websocket\websocket-common\9.4.43.v20210629\websocket-common-9.4.43.v20210629.jar;"%REPO%"\org\eclipse\jetty\websocket\websocket-api\9.4.43.v20210629\websocket-api-9.4.43.v20210629.jar;"%REPO%"\org\example\ChuckBot\1.0-SNAPSHOT\ChuckBot-1.0-SNAPSHOT.jar
set EXTRA_JVM_ARGUMENTS=
goto endInit

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS% %EXTRA_JVM_ARGUMENTS% -classpath %CLASSPATH_PREFIX%;%CLASSPATH% -Dapp.name="workerBot" -Dapp.repo="%REPO%" -Dbasedir="%BASEDIR%" bot.Main %CMD_LINE_ARGS%
if ERRORLEVEL 1 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=1

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
