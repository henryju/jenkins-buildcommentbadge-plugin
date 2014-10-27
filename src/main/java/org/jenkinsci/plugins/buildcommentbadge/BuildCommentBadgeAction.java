package org.jenkinsci.plugins.buildcommentbadge;

import hudson.Extension;
import hudson.PluginWrapper;
import hudson.model.BuildBadgeAction;
import hudson.model.Describable;
import hudson.model.AbstractBuild;
import hudson.model.Descriptor;
import hudson.model.Run;
import jenkins.model.RunAction2;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import javax.servlet.ServletException;

import java.io.IOException;

/**
 * Badge action of the build comment.
 * 
 * @author Julien Henry
 */
@ExportedBean
public class BuildCommentBadgeAction implements BuildBadgeAction, RunAction2, Describable<BuildCommentBadgeAction> {

  private transient/* final */AbstractBuild owner;

  public String comment;

  public BuildCommentBadgeAction(AbstractBuild<?, ?> owner) {
    this.owner = owner;
  }

  public BuildCommentBadgeAction(AbstractBuild<?, ?> owner, String comment) {
    this.owner = owner;
    this.comment = comment;
  }

  public AbstractBuild<?, ?> getOwner() {
    return owner;
  }

  public String getIcon() {
    return getIconPath("comment.png");
  }

  private static String getIconPath(String iconName) {
    PluginWrapper wrapper = Jenkins.getInstance().getPluginManager()
      .getPlugin(BuildCommentBadgePlugin.class);
    return "/plugin/" + wrapper.getShortName() + "/images/" + iconName;
  }

  public static BuildCommentBadgePlugin getPlugin() {
    return (BuildCommentBadgePlugin) Jenkins.getInstance().getPlugin(BuildCommentBadgePlugin.class);
  }

  // non use interface methods
  public String getIconFileName() {
    return getIconPath("comment.png");
  }

  public String getDisplayName() {
    return "Edit comment";
  }

  public String getUrlName() {
    return "comment";
  }

  @Exported(visibility = 2)
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void doIndex(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
    req.getView(this, "index.jelly").forward(req, rsp);
  }

  @Override
  public void onAttached(Run<?, ?> r) {
    // unnecessary, constructor already does this
  }

  @Override
  public void onLoad(Run<?, ?> r) {
    owner = (AbstractBuild) r;
  }

  public Descriptor<BuildCommentBadgeAction> getDescriptor() {
    return Jenkins.getInstance().getDescriptorOrDie(getClass());
  }

  /**
   * Edit the comment.
   */
  public void doSaveComment(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
    String comment = req.getSubmittedForm().getString("comment");
    setComment(StringUtils.trimToNull(comment));
    owner.save();
    rsp.sendRedirect(Stapler.getCurrentRequest().getContextPath() + '/' + owner.getUrl());
  }

  /**
   * Just for assisting form related stuff.
   */
  @Extension
  public static class DescriptorImpl extends Descriptor<BuildCommentBadgeAction> {
    public String getDisplayName() {
      return null;
    }

  }
}
