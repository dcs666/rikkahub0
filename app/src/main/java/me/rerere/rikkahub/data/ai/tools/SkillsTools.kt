package me.rerere.rikkahub.data.ai.tools

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import me.rerere.ai.core.InputSchema
import me.rerere.ai.core.Tool
import me.rerere.ai.ui.UIMessagePart
import me.rerere.rikkahub.data.files.SkillManager
import me.rerere.rikkahub.data.files.SkillMetadata

fun createSkillTools(
    enabledSkills: Set<String>,
    allSkills: List<SkillMetadata>,
    skillManager: SkillManager,
): List<Tool> {
    val available = allSkills.filter { it.name in enabledSkills }
    if (available.isEmpty()) return emptyList()

    return listOf(
        Tool(
            name = "use_skill",
            description = """
                Load and apply a skill to get specialized instructions or capabilities.
                Call this tool when the user's request matches one of the available skills.
            """.trimIndent(),
            systemPrompt = { _, _ ->
                buildString {
                    appendLine("**Auto-loaded Skills**")
                    appendLine("The following skills are automatically loaded. Read and follow their instructions as needed:")
                    available.forEach { skill ->
                        val body = skillManager.readSkillBody(skill.name) ?: return@forEach
                        appendLine()
                        appendLine("=== Skill: ${skill.name} ===")
                        // 大技能截取关键部分（前200行），避免撑爆上下文
                        val lines = body.trim().lines()
                        if (lines.size > 200) {
                            appendLine(lines.take(200).joinToString("\n"))
                            appendLine()
                            appendLine("[...技能较长，仅展示开头200行/共${lines.size}行。如需完整内容，使用 use_skill 工具加载]")
                        } else {
                            appendLine(body.trim())
                        }
                        appendLine("=== End ${skill.name} ===")
                    }
                }
            },
            parameters = {
                InputSchema.Obj(
                    properties = buildJsonObject {
                        put("name", buildJsonObject {
                            put("type", "string")
                            put("description", "The name of the skill to use")
                        })
                        put("path", buildJsonObject {
                            put("type", "string")
                            put(
                                "description",
                                "Optional relative path to a file inside the skill directory. Omit to read the default SKILL.md instructions. Only use paths extracted from Markdown links in the SKILL.md content. Do NOT guess or infer paths."
                            )
                        })
                    },
                    required = listOf("name")
                )
            },
            execute = {
                val name = it.jsonObject["name"]?.jsonPrimitive?.content
                    ?: error("name is required")
                if (name !in enabledSkills) {
                    error("Skill '$name' is not available. Available skills: ${enabledSkills.joinToString()}")
                }
                val path = it.jsonObject["path"]?.jsonPrimitive?.content
                val content = if (path.isNullOrBlank()) {
                    skillManager.readSkillBody(name)
                        ?: error("Skill '$name' not found")
                } else {
                    val target = skillManager.resolveSkillFile(name, path)
                        ?: error("Path '$path' is outside the skill directory")
                    require(target.exists()) { "File '$path' not found in skill '$name'" }
                    target.readText()
                }
                listOf(UIMessagePart.Text(content))
            }
        )
    )
}
