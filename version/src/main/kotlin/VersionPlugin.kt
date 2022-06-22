import common.BasePlugin
import org.gradle.api.Project

/**
 *
 * @Description:
 * @author : jianghaiyang
 * @date: 2022/6/22 00:07
 * @version: 1.0.0
 */
class VersionPlugin : BasePlugin() {
    override fun onCreate(project: Project) {
        addAppPlugin {

        }

        addLibraryPlugin {

        }

        addCommonPlugin {

        }
    }
}