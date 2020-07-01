package com.avioconsulting.mule.linter.model

class ProjectFile {

    File file

    ProjectFile(File f) {
        file = f
    }

    File getFile() {
        return file
    }

    void setFile(File file) {
        this.file = file
    }

    String getPath() {
        return file.absolutePath
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

}
