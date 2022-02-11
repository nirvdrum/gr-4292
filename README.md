# Introduction

This is a trivial reproduction case for Graal issue #4292. The project is not
intended to do anything useful. To evaluate the reported issue, you'll need to
have both GraalVM CE 22.0.0.2 and GraalVM EE 22.0.0 installed.

The issue is the difference is the generated header files for use with the
shared library built by `native-image`. In GraalVM CE, the function declarations
for creating and otherwise managing isolates with JNI are not generated. As far
as I can tell, the function declarations should be built in both CE and EE,
albeit with different target classes.

## How to Build

To build the native image for this project, run:

```
$ javac Bad.java
$ native-image --shared Bad
```

After building the project with both GraalVM CE and EE , the generated _bad.h_
header files should be compared.

## GraalVM EE 22.0.0 Output

Running on macOS 12.2, I see the following:

```
$ cat bad.h

#ifndef __BAD_H
#define __BAD_H

#include <graal_isolate.h>


#if defined(__cplusplus)
extern "C" {
#endif

int Bad__foo__92d352fd897d1c7c741cb628084797c0ea879a1a(graal_isolatethread_t*);

void dumpImageMethods();

int run_main(int argc, char** argv);

graal_isolatethread_t* Java_com_oracle_truffle_polyglot_enterprise_EnterprisePolyglotImpl_createIsolate();

graal_isolate_t* Java_com_oracle_truffle_polyglot_enterprise_EnterprisePolyglotImpl_getIsolateId(void * jniEnv, void * clazz, graal_isolatethread_t* isolateThread);

int Java_com_oracle_truffle_polyglot_enterprise_EnterprisePolyglotImpl_detachIsolate(void * jniEnv, void * clazz, graal_isolatethread_t* isolateThread);

graal_isolatethread_t* Java_com_oracle_truffle_polyglot_enterprise_EnterprisePolyglotImpl_attachIsolate(void * jniEnv, void * clazz, graal_isolate_t* isolate);

void vmLocatorSymbol(graal_isolatethread_t* thread);

#if defined(__cplusplus)
}
#endif
#endif
```

## GraalVM CE 22.0.0.2 Output

Running on macOS 12.2, I see the following:

```
$ cat bad.h

#ifndef __BAD_H
#define __BAD_H

#include <graal_isolate.h>


#if defined(__cplusplus)
extern "C" {
#endif

int Bad__foo__92d352fd897d1c7c741cb628084797c0ea879a1a(graal_isolatethread_t*);

int run_main(int argc, char** argv);

void vmLocatorSymbol(graal_isolatethread_t* thread);

#if defined(__cplusplus)
}
#endif
#endif
```
