package com.github.stachu540.dokka

import org.jetbrains.dokka.Formats.KotlinFormatDescriptorBase

open class MkDocsFormatDescriptor : KotlinFormatDescriptorBase() {
    override val formatServiceClass = MkDocsFormatService::class
}