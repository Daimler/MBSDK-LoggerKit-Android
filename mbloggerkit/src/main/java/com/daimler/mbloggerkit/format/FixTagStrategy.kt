package com.daimler.mbloggerkit.format

class FixTagStrategy(val tag: String) : TagStrategy {
    override fun createTag(tag: String?): String = this.tag
}