module ApplicationHelper
  def hidden_when_native = hotwire_native_app? ? "hidden" : ""
end
