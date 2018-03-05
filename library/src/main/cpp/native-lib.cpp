#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_superfactory_dcollection_LoginActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
