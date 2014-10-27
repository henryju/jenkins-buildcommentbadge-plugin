package org.jenkinsci.plugins.buildcommentbadge;

import hudson.Plugin;
import hudson.model.Descriptor.FormException;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import javax.servlet.ServletException;

import java.io.IOException;

/**
 * Plugin extension.
 * @author Julien Henry
 */
public class BuildCommentBadgePlugin extends Plugin {

  /** Indicates if the plugin is activated. */
  private boolean activated = true;

  public BuildCommentBadgePlugin() {

  }

  @DataBoundConstructor
  public BuildCommentBadgePlugin(boolean activated) {
    this.activated = activated;
  }

  @Override
  public void configure(StaplerRequest req, JSONObject formData)
    throws IOException, ServletException, FormException {

    super.configure(req, formData);
    // get activated value from system configuration save.
    this.setActivated(formData.getBoolean(FIELD_ACTIVATED));
    // serialize to XML
    this.save();
  }

  public boolean isActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  public static final String FIELD_ACTIVATED = "buildcommentbadge_activated";
}
